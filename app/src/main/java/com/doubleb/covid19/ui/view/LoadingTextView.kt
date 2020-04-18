package com.doubleb.covid19.ui.view

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.doubleb.covid19.R

class LoadingTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val alphaAnimation = AlphaAnimation(1.0f, 0.3f).apply {
        duration = 600
        repeatMode = Animation.REVERSE
        repeatCount = Animation.INFINITE
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        loading()
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility != VISIBLE) {
            clearLoadingAnimation()
        }
    }

    fun loading() {
        foreground = ContextCompat.getDrawable(context, R.drawable.shape_rectangle_loading)
        text = null
        startAnimation(alphaAnimation)
    }

    fun setLoadedText(text: String?) {
        this.text = text
        clearLoadingAnimation()
    }

    fun setLoadedText(spannableText: SpannableString) {
        this.text = spannableText
        clearLoadingAnimation()
    }

    private fun clearLoadingAnimation() {
        foreground = null
        clearAnimation()
    }
}