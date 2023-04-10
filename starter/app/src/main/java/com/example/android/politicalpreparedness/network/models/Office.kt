package com.example.android.politicalpreparedness.network.models

import com.squareup.moshi.Json

data class Office(
    val name: String,
    @Json(name = "divisionId") val divisionId: String,
    @Json(name = "officialIndices") val officials: List<Int>,
)
