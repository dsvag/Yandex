package com.dsvag.yandex.models.yandex


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
    @Json(name = "token")
    val token: String
)