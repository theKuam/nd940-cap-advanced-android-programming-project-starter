package com.example.android.politicalpreparedness.repositiory

import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.response.VoterInfoResponse
import com.example.android.politicalpreparedness.network.service.CivicService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface ElectionRepository {
    fun getUpcomingElections(): Flow<Result<List<Election>>>
    fun getSavedElections(): Flow<Result<List<Election>>>
    fun getVoterInfo(address: String, electionId: Int): Flow<Result<VoterInfoResponse>>
    fun saveElection(election: Election): Flow<Result<Unit>>
    fun removeElection(electionId: Int): Flow<Result<Unit>>
    fun checkSavedElection(electionId: Int): Flow<Result<Boolean>>
}

class ElectionRepositoryImpl(
    private val civicService: CivicService,
    private val electionDatabase: ElectionDatabase,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ElectionRepository {

    override fun getUpcomingElections(): Flow<Result<List<Election>>> = flow {
        val response = civicService.getUpcomingElections()
        emit(Result.Success(response.elections))
    }.flowOn(coroutineDispatcher)

    override fun getSavedElections(): Flow<Result<List<Election>>> = flow {
        emit(Result.Success(electionDatabase.electionDao.getAll()))
    }.flowOn(coroutineDispatcher)

    override fun getVoterInfo(address: String, electionId: Int): Flow<Result<VoterInfoResponse>> =
        flow {
            emit(Result.Success(civicService.getVoterInfo(address, electionId)))
        }.flowOn(coroutineDispatcher)

    override fun saveElection(election: Election): Flow<Result<Unit>> = flow {
        emit(Result.Success(electionDatabase.electionDao.insertElection(election)))
    }.flowOn(coroutineDispatcher)

    override fun removeElection(electionId: Int): Flow<Result<Unit>> = flow {
        emit(Result.Success(electionDatabase.electionDao.removeElection(electionId)))
    }.flowOn(coroutineDispatcher)

    override fun checkSavedElection(electionId: Int): Flow<Result<Boolean>> = flow {
        emit(Result.Success(electionDatabase.electionDao.isExist(electionId)))
    }.flowOn(coroutineDispatcher)
}
