package com.example.android.politicalpreparedness.usecase.election

import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.core.FlowUseCase
import com.example.android.politicalpreparedness.network.response.VoterInfoResponse
import com.example.android.politicalpreparedness.repositiory.ElectionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetVoterInfoUseCase constructor(
    private val electionRepository: ElectionRepository,
    coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<GetVoterInfoUseCase.Parameter, VoterInfoResponse>(coroutineDispatcher) {

    data class Parameter(
        val address: String,
        val electionId: Int,
    )

    override fun execute(parameters: Parameter): Flow<Result<VoterInfoResponse>> =
        electionRepository.getVoterInfo(parameters.address, parameters.electionId)
}
