package com.doubleb.covid19.extensions

import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

fun Long.shortValue(): String {
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val value = floor(log10(this.toDouble())).toInt()
    val base = value / 3

    return if (value >= 3 && base < suffix.size) {
        DecimalFormat("#0.0").format(
            this / 10.0.pow(base * 3.toDouble())
        ) + suffix[base]
    } else {
        DecimalFormat("#,##0").format(this)
    }
}