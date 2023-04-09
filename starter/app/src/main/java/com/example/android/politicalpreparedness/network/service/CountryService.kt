package com.example.android.politicalpreparedness.network.service

import com.example.android.politicalpreparedness.network.request.CountryRequest
import com.example.android.politicalpreparedness.network.response.StatesResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CountryService {

    @POST("countries/states")
    suspend fun getStates(
        @Body country: CountryRequest,
    ): StatesResponse
}