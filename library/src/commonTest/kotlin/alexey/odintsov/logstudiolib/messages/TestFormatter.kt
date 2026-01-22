package alexey.odintsov.logstudiolib.messages

import alexey.odintsov.logstudiolib.Formatter
import kotlinx.datetime.TimeZone
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TestFormatter: Formatter {
    val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    override fun formatDateTime(timeStampUs: Long): String {
        return dateTimeFormatter.format(Date(timeStampUs / 1000))
    }

    override fun formatTime(timeStampUs: Long): String {
        return timeFormatter.format(Date(timeStampUs / 1000))
    }

    override fun formatSizeHuman(size: Long): String {
        return size.toString()
    }

    override fun formatNumber(
        number: Number,
        format: String?
    ): String {
        return number.toString()
    }

    override fun setTimeZone(timeZone: TimeZone) = Unit

    override fun getTimeZone(): TimeZone {
        return TimeZone.currentSystemDefault()
    }

}