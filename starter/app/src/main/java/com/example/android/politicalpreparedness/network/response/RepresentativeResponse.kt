package com.example.android.politicalpreparedness.network.response

import com.example.android.politicalpreparedness.network.models.Office
import com.example.android.politicalpreparedness.network.models.Official
import com.example.android.politicalpreparedness.network.models.Representative
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepresentativeResponse(
    @Json(name = "offices")
    val offices: List<Office>,
    @Json(name = "officials")
    val officials: List<Official>,
)

fun RepresentativeResponse.toRepresentativeList(): List<Representative> {
    val representatives = arrayListOf<Representative>()
    var id = 0
    for (office in offices) {
        for (index in office.officials) {
            representatives.add(
                Representative(
                    id = id.toLong(),
                    office = office,
                    official = officials[index]
                )
            )
            id++
        }
    }
    return representatives
}