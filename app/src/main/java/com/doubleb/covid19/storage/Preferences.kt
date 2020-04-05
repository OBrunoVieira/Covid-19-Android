package com.doubleb.covid19.storage

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private const val FILE_NAME = "prefs"

    fun writeValue(context: Context, key: String, value: String) {
        with(getSharedPreferences(context).edit()) {
            putString(key, value)
            apply()
        }
    }

    fun readStringValue(context: Context, key: String) =
        getSharedPreferences(context).getString(key, null)

    private fun getSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
}