package alexey.odintsov.logstudiolib.messages

import alexey.odintsov.logstudiolib.Formatter
import androidx.compose.runtime.Stable

//@Serializable(with = MessageJsonSerializer::class)
//@Serializable(with = MessageCborSerializer::class)
@Stable
data class Message(
    val id: Int,
    val columns: Array<Any>,
) {
    companion object {
        const val PAYLOAD = "payload"
        const val TIMESTAMP = "timestamp"
        const val ID = "id"
        const val NUMBER = "number"
        const val LEVEL = "level"

        fun getContent(
            message: Message,
            payloadColumnIndex: Int,
        ): String? {
            return message.columns[payloadColumnIndex].toString()
        }

        fun formatValue(
            message: Message,
            columnIndex: Int,
            metaInfo: Map<String, String>,
            formatter: Formatter
        ): String {
            val value = message.columns[columnIndex]
            val columnValue = try {
                when {
                    metaInfo.containsKey(ID) -> message.id.toString()
                    metaInfo.containsKey(TIMESTAMP) -> formatter.formatDateTime(value as Long)
                    metaInfo.containsKey(NUMBER) -> formatter.formatNumber(
                        value as Float,
                        metaInfo[NUMBER]
                    )

                    else -> value?.toString() ?: ""
                }
            } catch (e: Exception) {
                "$e"
            }
            return columnValue
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Message

        if (id != other.id) return false
        if (columns != other.columns) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + columns.hashCode()
        return result
    }
}
