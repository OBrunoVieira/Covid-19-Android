package com.doubleb.covid19.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.doubleb.covid19.R
import kotlinx.android.synthetic.main.view_error.view.*
import okio.IOException

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val INTERNET_ERROR = 1100
        const val GENERIC_ERROR = -1
    }

    private var errorType = GENERIC_ERROR

    init {
        gravity = Gravity.CENTER
        orientation = VERTICAL

        View.inflate(context, R.layout.view_error, this)
    }

    fun errorType(throwable: Throwable?) = apply {
        this.errorType = if (throwable is IOException) INTERNET_ERROR else GENERIC_ERROR
    }

    fun reload(listener: OnClickListener) = apply {
        error_button.setOnClickListener { listener.onClick(it) }
    }

    fun build() = apply {
        error_text_view_title.run {
            val drawableRes =
                if (errorType == GENERIC_ERROR) {
                    R.drawable.selector_icon_error
                } else {
                    R.drawable.selector_icon_no_internet
                }

            setCompoundDrawablesRelativeWithIntrinsicBounds(0, drawableRes, 0, 0)
            text =
                resources.getString(
                    if (errorType == GENERIC_ERROR) {
                        R.string.error_generic_description
                    } else {
                        R.string.error_internet_description
                    }
                )
        }
    }
}