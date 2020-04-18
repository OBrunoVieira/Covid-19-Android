package com.doubleb.covid19.ui.view

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.doubleb.covid19.R
import com.doubleb.covid19.extensions.toPercentString
import kotlinx.android.synthetic.main.view_rate.view.*

class RateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var fatalityRate = 0.0
    private var recoveryRate = 0.0

    init {
        View.inflate(context, R.layout.view_rate, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        loading()
    }

    fun fatalityRate(fatalityRate: Double) = apply {
        this.fatalityRate = fatalityRate * 100
    }

    fun recoveryRate(recoveryRate: Double) = apply {
        this.recoveryRate = recoveryRate * 100
    }

    fun loading() {
        rate_text_view_recovery.loading()
        rate_text_view_fatality.loading()
    }

    fun build() {
        rate_text_view_recovery.setLoadedText(formatSpannableText(recoveryRate.toPercentString()))
        rate_text_view_fatality.setLoadedText(formatSpannableText(fatalityRate.toPercentString()))
    }

    private fun formatSpannableText(text: String) = SpannableString(text).apply {
        setSpan(
            RelativeSizeSpan(0.66f),
            length - 1,
            length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}