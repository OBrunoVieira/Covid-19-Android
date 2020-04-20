package com.doubleb.covid19.storage

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private const val FILE_NAME = "prefs"

    fun writeValue(context: Context, key: Key, value: String) {
        with(getSharedPreferences(context).edit()) {
            putString(key.name, value)
            apply()
        }
    }

    fun readStringValue(context: Context, key: Key) =
        getSharedPreferences(context).getString(key.name, null)

    private fun getSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
}