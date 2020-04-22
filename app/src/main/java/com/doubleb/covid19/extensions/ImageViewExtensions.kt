package com.doubleb.covid19.extensions

import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun ImageView.setBackgroundTint(@ColorRes colorRes: Int) = run {
    backgroundTintList = ContextCompat.getColorStateList(context, colorRes)
}
