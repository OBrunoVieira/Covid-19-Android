package com.doubleb.covid19.view_model

data class DataSource<T>(
    val dataState: DataState,
    val data: T? = null,
    val throwable: Throwable? = null
)