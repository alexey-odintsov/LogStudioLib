package alexey.odintsov.logstudiolib.messages

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object MessageCsvSerializer : KSerializer<Message> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("MessageCsv", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Message) {
        val cells = ArrayList<String>(1 + value.columns.size)
        cells.add(value.id.toString())
        for (c in value.columns) cells.add(c?.toString() ?: "")
        val csvLine = cells.joinToString(",") { csvEscape(it) }
        encoder.encodeString(csvLine)
    }

    override fun deserialize(decoder: Decoder): Message {
        val line = decoder.decodeString()
        val cells = parseCsvLine(line)
        if (cells.isEmpty()) throw SerializationException("Empty CSV line for Message")

        val id = cells[0].toIntOrNull()
            ?: throw SerializationException("Invalid id cell '${cells[0]}'")

        val cols: Array<Any?> = cells.drop(1).map { it }.toTypedArray()

        // Your Message is Array<Any> but we build Array<Any?> (CSV may represent empty as "").
        @Suppress("UNCHECKED_CAST")
        return Message(id = id, columns = cols as Array<Any?>)
    }

    private fun csvEscape(s: String): String {
        val needsQuotes = s.any { it == ',' || it == '"' || it == '\n' || it == '\r' }
        if (!needsQuotes) return s

        return buildString {
            append('"')
            for (ch in s) {
                if (ch == '"') append("\"\"") else append(ch)
            }
            append('"')
        }
    }

    /**
     * Parses ONE CSV line (no multiline fields).
     * Supports quoted fields and "" escaping.
     */
    private fun parseCsvLine(line: String): List<String> {
        val out = ArrayList<String>()
        val sb = StringBuilder()
        var i = 0
        var inQuotes = false

        while (i < line.length) {
            val ch = line[i]
            when {
                inQuotes && ch == '"' -> {
                    val nextIsQuote = (i + 1 < line.length && line[i + 1] == '"')
                    if (nextIsQuote) {
                        sb.append('"')
                        i += 2
                    } else {
                        inQuotes = false
                        i++
                    }
                }
                !inQuotes && ch == '"' -> {
                    inQuotes = true
                    i++
                }
                !inQuotes && ch == ',' -> {
                    out.add(sb.toString())
                    sb.setLength(0)
                    i++
                }
                else -> {
                    sb.append(ch)
                    i++
                }
            }
        }
        out.add(sb.toString())
        return out
    }
}