package com.entain.next.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val ANIMATED_COUNTER_LIMIT_IN_SECOND = 3600L //60 min
const val MIN_IN_SECOND = 3600L
const val SECONDS = 60L
const val SECOND_IN_MILL_SECOND = 1000L
const val YYYY_MM_DD_HH_MM = "yyyy MM dd HH:MM"


fun formatToDate(seconds: Long): String? {
    val time = System.currentTimeMillis() + seconds * SECOND_IN_MILL_SECOND
    val calender = Calendar.getInstance()
    calender.time = Date(time)
    val formatter = SimpleDateFormat(YYYY_MM_DD_HH_MM, Locale.getDefault())
    return formatter.format(calender.time)
}

fun currentTimeToSeconds(): Long {
    return (System.currentTimeMillis() / SECOND_IN_MILL_SECOND)
}