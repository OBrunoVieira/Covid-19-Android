package com.doublebb.tracking

import android.app.Activity
import androidx.annotation.MainThread
import androidx.annotation.Size
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics

object Tracking {

    @MainThread
    fun sendScreenView(
        activity: Activity,
        screenName: ScreenName
    ) {
        FirebaseAnalytics
            .getInstance(activity)
            .setCurrentScreen(
                activity,
                screenName.value,
                activity.javaClass.simpleName
            )
    }

    @MainThread
    fun sendScreenView(
        fragment: Fragment,
        screenName: ScreenName
    ) {
        FirebaseAnalytics
            .getInstance(fragment.requireActivity())
            .setCurrentScreen(
                fragment.requireActivity(),
                screenName.value,
                fragment.javaClass.simpleName
            )
    }
}