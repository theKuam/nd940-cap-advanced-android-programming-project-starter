package com.example.android.politicalpreparedness.ui.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.network.response.VoterInfoResponse
import com.example.android.politicalpreparedness.usecase.election.CheckSavedElectionUseCase
import com.example.android.politicalpreparedness.usecase.election.GetVoterInfoUseCase
import com.example.android.politicalpreparedness.usecase.election.RemoveElectionUseCase
import com.example.android.politicalpreparedness.usecase.election.SaveElectionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class VoterInfoViewModel(
    private val getVoterInfoUseCase: GetVoterInfoUseCase,
    private val checkSavedElectionUseCase: CheckSavedElectionUseCase,
    private val saveElectionUseCase: SaveElectionUseCase,
    private val removeElectionUseCase: RemoveElectionUseCase,
) : ViewModel() {

    companion object {
        private const val NO_ADDRESS =
            "No address, can not follow the election. Please visit find my representatives first."
    }

    private val _voterInfo = MutableStateFlow<VoterInfoResponse?>(null)
    val voterInfo: StateFlow<VoterInfoResponse?>
        get() = _voterInfo.asStateFlow()

    private val _saveState = MutableStateFlow(false)
    val saveState: StateFlow<Boolean>
        get() = _saveState.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorState: StateFlow<String>
        get() = _errorMessage

    fun getVoterInfo(address: String, electionId: Int) {
        viewModelScope.launch {
            getVoterInfoUseCase.invoke(GetVoterInfoUseCase.Parameter(address, electionId))
                .collectLatest { result ->
                    if (result is Result.Success) {
                        _voterInfo.value = result.data
                    } else if (result is Result.Error) {
                        _errorMessage.value = result.throwable.message.toString()
                    }
                }
        }
    }

    private fun followElection() {
        viewModelScope.launch {
            voterInfo.collectLatest { voterInfoResponse ->
                if (voterInfoResponse == null) {
                    _errorMessage.value = NO_ADDRESS
                } else {
                    voterInfoResponse.let {
                        saveElectionUseCase.invoke(it.election)
                            .collectLatest { result ->
                                if (result is Result.Success) {
                                    _saveState.value = true
                                } else if (result is Result.Error) {
                                    _errorMessage.value = result.throwable.message.toString()
                                }
                            }
                    }
                }
            }
        }
    }

    private fun unFollowElection(electionId: Int) {
        viewModelScope.launch {
            removeElectionUseCase.invoke(electionId)
                .collectLatest { result ->
                    if (result is Result.Success) {
                        _saveState.value = false
                    } else if (result is Result.Error) {
                        _errorMessage.value = result.throwable.message.toString()
                    }
                }
        }
    }

    fun onClickFollowButton(electionId: Int) {
        if (saveState.value) {
            unFollowElection(electionId)
        } else {
            followElection()
        }
    }

    fun checkSaveState(electionId: Int) {
        viewModelScope.launch {
            checkSavedElectionUseCase.invoke(electionId)
                .collectLatest { result ->
                    if (result is Result.Success) {
                        Timber.d("HaiNM18, ${result.data}")
                        _saveState.value = result.data
                    } else if (result is Result.Error) {
                        _errorMessage.value = result.throwable.message.toString()
                    }
                }
        }
    }

    fun resetErrorState() {
        _errorMessage.value = ""
    }
}