package com.doubleb.covid19.extensions

import android.view.View
import android.view.View.*

fun View.gone() = run {
    visibility = GONE
}

fun View.visible() = run {
    visibility = VISIBLE
}

fun View.invisible() = run {
    visibility = INVISIBLE
}