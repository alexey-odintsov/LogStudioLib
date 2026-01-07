package alexey.odintsov.logstudiolib.messages

import kotlinx.serialization.Serializable


@Serializable
data class ColumnData(
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
}