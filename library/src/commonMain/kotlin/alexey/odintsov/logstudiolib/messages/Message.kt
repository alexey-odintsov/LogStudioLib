package alexey.odintsov.logstudiolib.messages

import alexey.odintsov.logstudiolib.Formatter
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable(with = MessageJsonSerializer::class)
//@Serializable(with = MessageCborSerializer::class)
data class Message(
    val id: Int,
    val columns: Map<String, @Contextual Any>,
) {
    companion object {
        const val PAYLOAD = "payload"
        const val TIMESTAMP = "timestamp"
        const val NUMBER = "number"
        const val LEVEL = "level"

        fun getContent(
            message: Message,
            payloadColumnKey: String,
        ): String? {
            return message.columns[payloadColumnKey]?.toString()
        }

        fun formatValue(message: Message, key: String, metaInfo: Map<String, String>, formatter: Formatter): String {
            val value = message.columns[key]
            val columnValue = try { when {
                key == "id" -> message.id.toString()
                metaInfo.containsKey(TIMESTAMP) -> formatter.formatDateTime(value as Long)
                metaInfo.containsKey(NUMBER) -> formatter.formatNumber(value as Float, metaInfo[NUMBER])
                else -> value?.toString()?: ""
            } } catch (e: Exception) {
                "$e"
            }
            return columnValue
        }
    }
}
