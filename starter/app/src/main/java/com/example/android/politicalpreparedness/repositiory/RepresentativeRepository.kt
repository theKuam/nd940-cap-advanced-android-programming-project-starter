package com.example.android.politicalpreparedness.repositiory

import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.model.Representative
import com.example.android.politicalpreparedness.network.request.CountryRequest
import com.example.android.politicalpreparedness.network.response.toRepresentativeList
import com.example.android.politicalpreparedness.network.service.CivicService
import com.example.android.politicalpreparedness.network.service.CountryService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface RepresentativeRepository {
    fun getStates(country: String): Flow<Result<List<String>>>
    fun getRepresentatives(address: String): Flow<Result<List<Representative>>>
}

class RepresentativeRepositoryImpl(
    private val countryService: CountryService,
    private val civicService: CivicService,
    private val coroutineDispatcher: CoroutineDispatcher,
) : RepresentativeRepository {
    override fun getStates(country: String): Flow<Result<List<String>>> = flow {
        val statesResponse = countryService.getStates(CountryRequest(country))
        val states = statesResponse.data.states
        emit(Result.Success(states.map { it.name }))
    }.flowOn(coroutineDispatcher)

    override fun getRepresentatives(address: String): Flow<Result<List<Representative>>> = flow {
        val representativeResponse = civicService.getRepresentatives(address)
        emit(Result.Success(representativeResponse.toRepresentativeList()))
    }.flowOn(coroutineDispatcher)
}
