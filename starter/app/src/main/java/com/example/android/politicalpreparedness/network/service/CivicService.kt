package com.example.android.politicalpreparedness.network.service

import com.example.android.politicalpreparedness.network.response.ElectionResponse
import com.example.android.politicalpreparedness.network.response.RepresentativeResponse
import com.example.android.politicalpreparedness.network.response.VoterInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CivicService {

    //TODO: Add voterinfo API Call

    @GET("representatives")
    suspend fun getRepresentatives(
        @Query("address") address: String,
    ): RepresentativeResponse

    @GET("elections")
    suspend fun getUpcomingElections(): ElectionResponse


    @GET("voterinfo")
    suspend fun getVoterInfo(
        @Query("address") address: String,
        @Query("electionId") electionId: Int,
    ): VoterInfoResponse
}