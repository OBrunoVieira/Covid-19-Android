package com.doubleb.covid19.extensions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

fun Long.shortValue(): String = run {
    val decimalFormat = NumberFormat.getNumberInstance(Locale.getDefault()) as DecimalFormat
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val value = floor(log10(this.toDouble())).toInt()
    val base = value / 3

    return@run if (value >= 3 && base < suffix.size) {
        decimalFormat.applyPattern("#0.0")
        decimalFormat.format(this / 10.0.pow(base * 3.toDouble())) + suffix[base]
    } else {
        decimalFormat.applyPattern("#,##0")
        decimalFormat.format(this)
    }
}

fun Double.toPercentString(): String =
    DecimalFormat("#.##'%'", DecimalFormatSymbols(Locale.getDefault())).format(this)