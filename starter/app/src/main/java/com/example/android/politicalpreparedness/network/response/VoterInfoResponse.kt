package com.example.android.politicalpreparedness.network.response

import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionOfficial
import com.example.android.politicalpreparedness.network.models.State
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VoterInfoResponse(
    val election: Election,
//    val pollingLocations: String? = null,
//    val contests: String? = null,
    val state: List<State>? = null,
    val electionElectionOfficials: List<ElectionOfficial>? = null,
)