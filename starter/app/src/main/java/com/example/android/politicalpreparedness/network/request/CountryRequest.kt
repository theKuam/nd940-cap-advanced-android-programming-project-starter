package com.example.android.politicalpreparedness.network.request

import com.squareup.moshi.Json

data class CountryRequest(
    @Json(name = "country")
    val country: String,
)
