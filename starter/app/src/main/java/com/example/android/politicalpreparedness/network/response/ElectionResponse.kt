package com.example.android.politicalpreparedness.network.response

import com.example.android.politicalpreparedness.network.models.Election
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ElectionResponse(
    val kind: String,
    val elections: List<Election>,
)