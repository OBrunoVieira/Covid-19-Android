package com.doubleb.covid19.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.takeLast(days: Int) = run {
    time = Date()
    add(Calendar.DAY_OF_MONTH, -days)
    return@run Date(timeInMillis)
}

fun String.convertZuluToTargetFormat(targetFormat : String): String = run {
    val zuluTime = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        Locale.getDefault()
    ).parse(this) ?: Date()

    return@run SimpleDateFormat(targetFormat, Locale.getDefault())
        .format(zuluTime)
}