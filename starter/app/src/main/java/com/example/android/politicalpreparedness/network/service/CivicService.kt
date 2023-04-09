package com.example.android.politicalpreparedness.network.service

import com.example.android.politicalpreparedness.network.response.RepresentativeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CivicService {
    //TODO: Add elections API Call

    //TODO: Add voterinfo API Call

    @GET("representatives")
    suspend fun getRepresentatives(
        @Query("address") address: String,
    ): RepresentativeResponse
}