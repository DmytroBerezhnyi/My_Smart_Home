package com.example.domain.extensions

import java.text.SimpleDateFormat
import java.util.*

const val PATTERN_DATE_TIME = "dd.MM.yyyy HH:mm"
const val ONE_MINUTE_IN_MILLIS: Long = 60000

fun getCurrentDate(): Date = getDateTimeAsString().toDate()

fun getDateTimeAsString(): String {
    val sdf = SimpleDateFormat(PATTERN_DATE_TIME, Locale.getDefault())
    return sdf.format(Date())
}

fun String.toDate(): Date {
    return SimpleDateFormat(PATTERN_DATE_TIME, Locale.getDefault()).parse(this) as Date
}

fun Date.toDateString(): String {
    val formatter = SimpleDateFormat(PATTERN_DATE_TIME, Locale.getDefault())
    return formatter.format(this)
}

fun Date.addMinutesToDate(minutes: Long) = Date(this.time + minutes * ONE_MINUTE_IN_MILLIS)
