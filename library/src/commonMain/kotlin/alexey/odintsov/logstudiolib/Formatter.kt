package alexey.odintsov.logstudiolib

import kotlinx.datetime.TimeZone

interface Formatter {
    fun formatDateTime(timeStampUs: Long): String
    fun formatTime(timeStampUs: Long): String
    fun formatSizeHuman(size: Long): String
    fun formatNumber(number: Number, format: String?): String

    fun setTimeZone(timeZone: TimeZone)
    fun getTimeZone(): TimeZone

    companion object {
        val STUB = object : Formatter {
            override fun formatDateTime(timeStampUs: Long): String = timeStampUs.toString()
            override fun formatTime(timeStampUs: Long): String = timeStampUs.toString()
            override fun formatSizeHuman(size: Long): String = size.toString()
            override fun formatNumber(number: Number, format: String?): String = number.toString()
            override fun setTimeZone(timeZone: TimeZone) = Unit
            override fun getTimeZone(): TimeZone = TimeZone.currentSystemDefault()
        }
    }
}