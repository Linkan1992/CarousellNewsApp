package com.linkan.carousellnewsapp.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

object UtilHelper {

    fun createdTimeAgoString(createdTime : Long): String {
        val now = LocalDateTime.now()
        val createdTime = Instant.ofEpochSecond(createdTime)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val seconds = ChronoUnit.SECONDS.between(createdTime, now)

        fun format(unit: String, value: Long): String =
            "$value $unit${if (value != 1L) "s" else ""} ago"

        return when {
            seconds < 2 -> "$seconds second ago"
            seconds < 60 -> format("second", seconds)
            seconds < 120 -> format("minute", 1)
            seconds < 3600 -> format("minute", seconds / 60)
            seconds < 7200 -> format("hour", 1)
            seconds < 86400 -> format("hour", seconds / 3600)
            seconds < 172800 -> format("day", 1)
            seconds < 604800 -> format("day", seconds / 86400)
            seconds < 1209600 -> format("week", 1)
            seconds < 2419200 -> format("week", seconds / 604800)
            seconds < 4838400 -> format("month", 1)
            seconds < 29030400 -> format("month", seconds / 2419200)
            seconds < 58060800 -> format("year", 1)
            else -> format("year", seconds / 29030400)
        }
    }

}