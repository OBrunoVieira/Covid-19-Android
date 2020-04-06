package com.doubleb.covid19.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.doubleb.covid19.R
import com.doubleb.covid19.extensions.compoundTopDrawable
import kotlinx.android.synthetic.main.view_error.view.*
import okio.IOException

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val INTERNET_ERROR = 1100
        const val UNDER_CONSTRUCTION = 1101
        const val GENERIC_ERROR = -1
    }

    private var errorType = GENERIC_ERROR

    init {
        gravity = Gravity.CENTER
        orientation = VERTICAL

        View.inflate(context, R.layout.view_error, this)
    }

    fun throwable(throwable: Throwable?) = apply {
        this.errorType = if (throwable is IOException) INTERNET_ERROR else GENERIC_ERROR
    }

    fun errorType(errorType: Int) = apply {
        this.errorType = errorType
    }

    fun reload(listener: OnClickListener) = apply {
        error_button.setOnClickListener { listener.onClick(it) }
    }

    fun build() = apply {
        when (errorType) {
            GENERIC_ERROR -> {
                error_text_view_description.visibility = GONE
                error_button.visibility = VISIBLE

                error_text_view_title.compoundTopDrawable(R.drawable.selector_icon_error)
                error_text_view_title.setText(R.string.error_generic_title)
            }

            UNDER_CONSTRUCTION -> {
                error_text_view_description.visibility = VISIBLE
                error_button.visibility = GONE

                error_text_view_title.compoundTopDrawable(R.drawable.selector_icon_cone)
                error_text_view_title.setText(R.string.error_under_construction_title)
                error_text_view_description.setText(R.string.error_under_construction_description)
            }

            else -> {
                error_text_view_description.visibility = GONE
                error_button.visibility = VISIBLE

                error_text_view_title.compoundTopDrawable(R.drawable.selector_icon_no_internet)
                error_text_view_title.setText(R.string.error_internet_title)
            }
        }
    }
}