package com.example.android.politicalpreparedness.usecase.election

import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.core.FlowUseCase
import com.example.android.politicalpreparedness.repositiory.ElectionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class CheckSavedElectionUseCase constructor(
    private val electionRepository: ElectionRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Int, Boolean>(coroutineDispatcher) {
    override fun execute(parameters: Int): Flow<Result<Boolean>> =
        electionRepository.checkSavedElection(parameters)
}