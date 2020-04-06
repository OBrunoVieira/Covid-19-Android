package com.doubleb.covid19.model

import com.google.gson.annotations.SerializedName

data class TimeLine(
    @SerializedName("cases") val casesMap: LinkedHashMap<String, Int> = linkedMapOf(),
    @SerializedName("deaths") val deathsMap: LinkedHashMap<String, Int> = linkedMapOf(),
    @SerializedName("recovered") val recoveredMap: LinkedHashMap<String, Int> = linkedMapOf()
)