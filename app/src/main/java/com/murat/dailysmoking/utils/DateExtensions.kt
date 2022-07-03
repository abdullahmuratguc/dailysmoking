package com.murat.dailysmoking.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Murat
 */

fun Date.toFormat(dateFormat: String): String {
    return SimpleDateFormat(dateFormat, Locale.getDefault()).format(this)
}

fun Date.startOfDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    return calendar.time
}

fun startOfYear(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_YEAR, 1)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    return calendar.time
}

fun Date.lastDayOfMonth(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    return calendar.time
}

fun currentWeekStartDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    calendar.firstDayOfWeek = Calendar.MONDAY
    return calendar.time
}

fun currentMonthTotalDay(): Int {
    val calendar = Calendar.getInstance()
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

fun currentMonthStartDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    return calendar.time
}

fun Date.currentMonthBeginForGraph(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DATE, -currentMonthTotalDay() + 1)
    return cal.time
}

fun Date.currentWeekBeginForGraph(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DATE, -6)
    return cal.time
}

fun Date.prevDay(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DATE, -1)
    return cal.time
}

fun Date.nextDay(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DATE, 1)
    return cal.time
}

fun Date.nextMonth(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.MONTH, 1)
    return cal.time
}

fun Date.endOfDay(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    return calendar.time
}