package alexey.odintsov.logstudiolib.messages

import org.junit.Test
import kotlin.test.assertEquals


class MessageTest {
    private val formatter = TestFormatter()

    @Test
    fun `Test formatValue for null value`() {
        val columns = Array<Any?>(1) { Any() }
        columns[0] = null
        val message = Message(1, columns)
        val actual = Message.formatValue(message, 0, mapOf(), formatter)
        assertEquals("", actual)
    }

    @Test
    fun `Test formatValue for String value`() {
        val columns = Array<Any?>(1) { Any() }
        columns[0] = "Abc"
        val message = Message(1, columns)
        val actual = Message.formatValue(message, 0, mapOf(), formatter)
        assertEquals("Abc", actual)
    }

    @Test
    fun `Test formatValue for Id value`() {
        val columns = Array<Any?>(1) { Any() }
        columns[0] = 1
        val message = Message(1, columns)
        val actual = Message.formatValue(message, 0, mapOf(Message.ID to "true"), formatter)
        assertEquals("1", actual)
    }

    @Test
    fun `Test formatValue for Float value`() {
        val columns = Array<Any?>(1) { Any() }
        columns[0] = 1.5f
        val message = Message(1, columns)
        val actual = Message.formatValue(message, 0, mapOf(Message.NUMBER to "true"), formatter)
        assertEquals("1.5", actual)
    }

    @Test
    fun `Test formatValue for Timestamp value`() {
        val columns = Array<Any?>(1) { Any() }
        columns[0] = 1737552000000000L
        val message = Message(1, columns)
        val actual = Message.formatValue(message, 0, mapOf(Message.TIMESTAMP to "true"), formatter)
        assertEquals("2025-01-22 14:20:00", actual)
    }

    @Test
    fun `Test formatValue for Time value`() {
        val columns = Array<Any?>(1) { Any() }
        columns[0] = 1737552000000000L
        val message = Message(1, columns)
        val actual = Message.formatValue(message, 0, mapOf(Message.TIME to "true"), formatter)
        assertEquals("14:20:00", actual)
    }
}