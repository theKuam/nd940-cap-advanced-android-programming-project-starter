package com.example.android.politicalpreparedness.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StatesResponse(
    @Json(name = "error")
    val isError: Boolean,
    @Json(name = "msg")
    val message: String,
    @Json(name = "data")
    val data: CountryStateResponse,
) {
    @JsonClass(generateAdapter = true)
    data class CountryStateResponse(
        @Json(name = "name")
        val country: String,
        @Json(name = "iso3")
        val countryCode: String,
        @Json(name = "states")
        val states: List<StatesResponse>,
    )

    @JsonClass(generateAdapter = true)
    data class StatesResponse(
        @Json(name = "name")
        val name: String,
        @Json(name = "state_code")
        val stateCode: String,
    )
}
