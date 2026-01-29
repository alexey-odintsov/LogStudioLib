package alexey.odintsov.logstudiolib.messages

import kotlinx.serialization.Serializable


@Serializable
data class ColumnData(
    val id: Int,
    val key: String,
    val title: String,
    val size: Float = 0f,
    val weight: Float? = 1f,
    val visible: Boolean = true,
    val order: Int,
    val align: Align = Align.Left,
    val metaInfo: HashMap<String, String> = hashMapOf()
) {
    enum class Align {
        Left, Center, Right,
    }

    companion object {
        fun getPayloadColumnIndex(columns: List<ColumnData>): Int {
            return columns.firstOrNull { c -> c.metaInfo.contains(Message.PAYLOAD) }?.order ?: -1
        }

        fun getPayloadColumnIndex(columns: Array<ColumnData>): Int {
            return columns.firstOrNull { c -> c.metaInfo.contains(Message.PAYLOAD) }?.order ?: -1
        }
    }
}