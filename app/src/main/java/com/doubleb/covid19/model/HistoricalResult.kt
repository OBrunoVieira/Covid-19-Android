package com.doubleb.covid19.model

class HistoricalResult(
    val country: String = "",
    val cases: List<TimeLineResult>,
    val deaths: List<TimeLineResult>,
    val recovered: List<TimeLineResult>
)