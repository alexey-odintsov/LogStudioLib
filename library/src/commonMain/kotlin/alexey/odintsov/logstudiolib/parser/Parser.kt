package alexey.odintsov.logstudiolib.parser

import alexey.odintsov.logstudiolib.messages.ColumnData
import alexey.odintsov.logstudiolib.messages.Message


interface Parser {
    suspend fun parseFiles(
        filePaths: List<String>,
        progressCallback: (String, Float) -> Unit
    ): List<Message>

    suspend fun parse(
        source: ParserDataSource,
        progressCallback: (String, Float) -> Unit,
        onMessageParsed: (Message) -> Unit,
    )

    fun knownFileExtensions(): List<String>
    fun getColumns(): List<ColumnData>

    enum class Type(val id: Int) {
        /**
         * Static compiled parser
         */
        Static(0),

        /**
         * Parsers text file according to columns definition and regex pattern
         */
        Text(1),

        /**
         * External app that produces parsed Messages list
         */
        External(2),
    }
}