package com.doublebb.tracking
import androidx.annotation.Size

enum class ScreenName(@Size(min = 1L, max = 36L) val value: String) {
    SPLASH("Splash"),
    HOME("Home"),
    WORLD("World"),
    SEARCH("Search"),
    NEWS("News"),
    PREVENTIONS("Preventions"),
    SYMPTOMS("Symptoms"),
    COUNTRY_DETAILS("Country Details"),
}