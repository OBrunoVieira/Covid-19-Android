package com.doubleb.covid19.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isNetworkConnected() = run {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCapabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    return@run networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true ||
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
}