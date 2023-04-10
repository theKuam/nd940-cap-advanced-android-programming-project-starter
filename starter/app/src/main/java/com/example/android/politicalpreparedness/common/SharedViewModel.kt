package com.example.android.politicalpreparedness.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.android.politicalpreparedness.common.util.FirebaseUserLiveData
import com.example.android.politicalpreparedness.network.models.Address
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    private val _sharedAddress = MutableStateFlow("")
    val sharedAddress: StateFlow<String>
        get() = _sharedAddress.asStateFlow()

    private val _electionName = MutableStateFlow("")
    val electionName: StateFlow<String>
        get() = _electionName.asStateFlow()

    fun setSharedAddress(address: Address?) {
        _sharedAddress.value = address?.toFormattedString() ?: ""
    }

    fun setElectionName(electionName: String?) {
        _electionName.value = electionName ?: ""
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}