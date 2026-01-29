package alexey.odintsov.logstudiolib.messages

interface MessagesConsumer {
    fun onMessagesLoaded(messages: List<Message>, columns: List<ColumnData>)
}