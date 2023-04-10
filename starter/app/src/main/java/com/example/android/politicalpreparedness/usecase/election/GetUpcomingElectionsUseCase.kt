package com.example.android.politicalpreparedness.usecase.election

import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.core.FlowUseCase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repositiory.ElectionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetUpcomingElectionsUseCase constructor(
    private val electionRepository: ElectionRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, List<Election>>(coroutineDispatcher) {
    override fun execute(parameters: Unit): Flow<Result<List<Election>>> =
        electionRepository.getUpcomingElections()
}