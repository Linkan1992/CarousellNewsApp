package com.linkan.carousellnewsapp.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

object UtilHelper {

    fun createdTimeAgoString(epochSeconds: Long): String {
        val created = Instant.ofEpochSecond(epochSeconds)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        val now = LocalDateTime.now()

        val seconds = ChronoUnit.SECONDS.between(created, now)

        val minute = 60
        val hour = 60 * minute
        val day = 24 * hour
        val week = 7 * day
        val month = 30 * day
        val year = 365 * day

        val (value, unit) = when {
            seconds < minute -> seconds to "second"
            seconds < hour -> (seconds / minute) to "minute"
            seconds < day -> (seconds / hour) to "hour"
            seconds < week -> (seconds / day) to "day"
            seconds < month -> (seconds / week) to "week"
            seconds < year -> (seconds / month) to "month"
            else -> (seconds / year) to "year"
        }

        return "$value $unit${if (value != 1L) "s" else ""} ago"
    }

}