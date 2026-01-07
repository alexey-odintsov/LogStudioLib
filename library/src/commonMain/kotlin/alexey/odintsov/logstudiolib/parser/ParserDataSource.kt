package alexey.odintsov.logstudiolib.parser

import java.io.InputStream

interface ParserDataSource {
    fun getStream(): InputStream
}