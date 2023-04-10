package com.example.android.politicalpreparedness.ui.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.usecase.election.GetSavedElectionsUseCase
import com.example.android.politicalpreparedness.usecase.election.GetUpcomingElectionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(
    private val getUpcomingElectionsUseCase: GetUpcomingElectionsUseCase,
    private val getSavedElectionsUseCase: GetSavedElectionsUseCase,
) : ViewModel() {

    private val _upcomingElections = MutableStateFlow<List<Election>>(listOf())
    val upcomingElections: StateFlow<List<Election>>
        get() = _upcomingElections.asStateFlow()

    private val _savedElections = MutableStateFlow<List<Election>>(listOf())
    val savedElections: StateFlow<List<Election>>
        get() = _savedElections.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorState: StateFlow<String>
        get() = _errorMessage

    fun fetchUpcomingElections() {
        viewModelScope.launch {
            getUpcomingElectionsUseCase.invoke(Unit)
                .collectLatest { result ->
                    if (result is Result.Success) {
                        _upcomingElections.value = result.data
                    } else if (result is Result.Error) {
                        _errorMessage.value = result.throwable.message.toString()
                    }
                }
        }
    }

    fun getSavedElections() {
        viewModelScope.launch {
            getSavedElectionsUseCase.invoke(Unit)
                .collectLatest { result ->
                    if (result is Result.Success) {
                        _savedElections.value = result.data
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