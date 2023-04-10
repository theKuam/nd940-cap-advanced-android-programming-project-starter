package com.example.android.politicalpreparedness.usecase.election

import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.core.FlowUseCase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repositiory.ElectionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class SaveElectionUseCase constructor(
    private val electionRepository: ElectionRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Election, Unit>(coroutineDispatcher) {
    override fun execute(parameters: Election): Flow<Result<Unit>> =
        electionRepository.saveElection(parameters)
}