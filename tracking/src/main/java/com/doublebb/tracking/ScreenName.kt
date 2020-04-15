package com.doublebb.tracking
import androidx.annotation.Size

enum class ScreenName(@Size(min = 1L, max = 36L) val value: String) {
    SPLASH("Splash"),
    HOME("Home"),
    SEARCH("Search"),
    NEWS("News"),
    PREVENTIONS("Preventions"),
    SYMPTOMS("Symptoms"),
    COUNTRY_DETAILS("Country Details"),
    OVERVIEW("Overview"),
    COUNTRIES("Countries")
}