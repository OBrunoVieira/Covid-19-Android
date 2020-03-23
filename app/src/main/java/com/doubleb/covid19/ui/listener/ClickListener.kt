package com.doubleb.covid19.ui.listener

interface ClickListener<T> {
    fun onItemClicked(data: T?, position: Int)
}