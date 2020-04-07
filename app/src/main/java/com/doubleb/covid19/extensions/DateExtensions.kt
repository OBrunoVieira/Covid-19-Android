package com.doubleb.covid19.extensions

import java.util.*

fun Calendar.takeLast(days: Int) = run {
    time = Date()
    add(Calendar.DAY_OF_MONTH, -days)
    return@run Date(timeInMillis)
}