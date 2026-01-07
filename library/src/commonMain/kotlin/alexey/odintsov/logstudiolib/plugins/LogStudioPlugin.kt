package alexey.odintsov.logstudiolib.plugins

interface LogStudioPlugin {
    fun name(): String

    fun description(): String

    fun author(): String

    fun pluginLink(): String?

    fun version(): String

    fun directoryName(): String

    fun className(): String

    fun init(pluginDirectoryPath: String)
}
