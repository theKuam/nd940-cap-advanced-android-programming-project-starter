package com.example.android.politicalpreparedness.usecase.election

import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.core.FlowUseCase
import com.example.android.politicalpreparedness.repositiory.ElectionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class RemoveElectionUseCase constructor(
    private val electionRepository: ElectionRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Int, Unit>(coroutineDispatcher) {
    override fun execute(parameters: Int): Flow<Result<Unit>> =
        electionRepository.removeElection(parameters)
}