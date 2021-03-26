package com.dsvag.yandex.models.finnhub

import com.squareup.moshi.Json

data class StockInfo(
    @Json(name = "name")
    val company: String,

    @Json(name = "ticker")
    val ticker: String,
)