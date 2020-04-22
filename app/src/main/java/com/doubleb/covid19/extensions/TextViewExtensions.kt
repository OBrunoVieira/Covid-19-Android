package com.doubleb.covid19.extensions

import android.widget.TextView
import androidx.annotation.DrawableRes

fun TextView.compoundTopDrawable(@DrawableRes drawableRes: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(0, drawableRes, 0, 0)
}

fun TextView.compoundStartDrawable(@DrawableRes drawableRes: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(drawableRes, 0, 0, 0)
}