package com.hfad.stopwatch.utils

/***
 * Переводим миллисекунды в читаемый формат
 */
object TimestampMillisecondsFormatter {
    private const val MILLISECONDS = 1000
    private const val SECONDS = 60
    private const val MINUTES = 60

    fun format(timestamp: Long): String {
        val millisecondsFormatted = (timestamp % MILLISECONDS).pad(3)
        val seconds = timestamp / MILLISECONDS
        val secondsFormatted = (seconds % SECONDS).pad(2)
        val minutes = seconds / SECONDS
        val minutesFormatted = (minutes % MINUTES).pad(2)
        val hours = minutes / MINUTES
        return if (hours > 0) {
            val hoursFormatted = (minutes / MINUTES).pad(2)
            "$hoursFormatted:$minutesFormatted:$secondsFormatted"
        } else {
            "$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
        }
    }

    private fun Long.pad(desiredLength: Int) =
        this.toString().padStart(desiredLength, '0')

}