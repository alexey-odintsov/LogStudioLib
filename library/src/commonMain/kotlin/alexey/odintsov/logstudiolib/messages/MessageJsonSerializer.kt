package alexey.odintsov.logstudiolib.messages

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

object MessageJsonSerializer : KSerializer<Message> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Message") {
        element<Int>("id")
        element<List<JsonElement>>("columns") // Changed to List
    }

    override fun serialize(encoder: Encoder, value: Message) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: error("MessageSerializer only supports JSON")

        val jsonColumns = buildJsonArray {
            value.columns.forEach { v ->
                add(toJsonElement(v))
            }
        }

        val json = buildJsonObject {
            put("id", JsonPrimitive(value.id))
            put("columns", jsonColumns)
        }

        jsonEncoder.encodeJsonElement(json)
    }

    override fun deserialize(decoder: Decoder): Message {
        val jsonDecoder = decoder as? JsonDecoder
            ?: error("MessageSerializer only supports JSON")

        val json = jsonDecoder.decodeJsonElement().jsonObject
        val id = json["id"]!!.jsonPrimitive.int
        val columnsJson = json["columns"]!!.jsonArray

        val columns: Array<Any?> = columnsJson.map { fromJsonElement(it) }.toTypedArray()

        return Message(id, columns)
    }

    private fun toJsonElement(value: Any?): JsonElement = when (value) {
        null -> JsonNull
        is Number -> JsonPrimitive(value)
        is Boolean -> JsonPrimitive(value)
        is String -> JsonPrimitive(value)
        is Map<*, *> -> buildJsonObject {
            value.forEach { (k, v) ->
                if (k is String) put(k, toJsonElement(v))
            }
        }

        is List<*> -> buildJsonArray {
            value.forEach { add(toJsonElement(it)) }
        }

        else -> JsonPrimitive(value.toString()) // fallback
    }

    private fun fromJsonElement(el: JsonElement): Any? = when {
        el is JsonNull -> null
        el is JsonPrimitive && el.isString -> el.content
        el is JsonPrimitive -> el.booleanOrNull
            ?: el.longOrNull
            ?: el.doubleOrNull?.toFloat()  // Convert Double to Float
            ?: el.content
        el is JsonObject -> el.mapValues { fromJsonElement(it.value) }
        el is JsonArray -> el.map { fromJsonElement(it) }
        else -> el.toString()
    }
}