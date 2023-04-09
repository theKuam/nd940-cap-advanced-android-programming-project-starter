package com.example.android.politicalpreparedness.network.models

import com.example.android.politicalpreparedness.model.Representative
import com.squareup.moshi.Json

data class Office(
    val name: String,
    @Json(name = "divisionId") val divisionId: String,
    @Json(name = "officialIndices") val officials: List<Int>,
) {
    fun getRepresentatives(officials: List<Official>): List<Representative> {
        return this.officials.map { index ->
            Representative(index.toLong(), officials[index], this)
        }
    }
}
