package alexey.odintsov.logstudiolib.messages

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

//object MessageCborSerializer : KSerializer<Message> {
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Message") {
//        element<Int>("id")
//        element("columns", MapSerializer(String.serializer(), String.serializer()).descriptor)
//    }
//
//    override fun serialize(encoder: Encoder, value: Message) {
//        val composite = encoder.beginStructure(descriptor)
//        composite.encodeIntElement(descriptor, 0, value.id)
//
//        // encode columns manually
//        val map = value.columns.mapValues { it.value.toString() } // simple fallback
//        composite.encodeSerializableElement(
//            descriptor,
//            1,
//            MapSerializer(String.serializer(), String.serializer()),
//            map
//        )
//        composite.endStructure(descriptor)
//    }
//
//    override fun deserialize(decoder: Decoder): Message {
//        val dec = decoder.beginStructure(descriptor)
//        var id = 0
//        var columns = emptyMap<String, Any>()
//
//        loop@ while (true) {
//            when (val index = dec.decodeElementIndex(descriptor)) {
//                0 -> id = dec.decodeIntElement(descriptor, 0)
//                1 -> {
//                    val map = dec.decodeSerializableElement(
//                        descriptor,
//                        1,
//                        MapSerializer(String.serializer(), String.serializer())
//                    )
//                    columns = map
//                }
//
//                CompositeDecoder.DECODE_DONE -> break@loop
//            }
//        }
//
//        dec.endStructure(descriptor)
//        return Message(id, columns)
//    }
//}