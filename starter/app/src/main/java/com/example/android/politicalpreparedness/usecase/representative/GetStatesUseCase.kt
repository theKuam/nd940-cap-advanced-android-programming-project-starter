package com.example.android.politicalpreparedness.usecase.representative

import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.core.FlowUseCase
import com.example.android.politicalpreparedness.repositiory.RepresentativeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetStatesUseCase constructor(
    private val representativeRepository: RepresentativeRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<String, List<String>>(coroutineDispatcher) {
    override fun execute(parameters: String): Flow<Result<List<String>>> =
        representativeRepository.getStates(parameters)
}
