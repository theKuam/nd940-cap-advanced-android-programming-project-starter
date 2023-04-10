package com.example.android.politicalpreparedness.ui.representative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.common.Result
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Representative
import com.example.android.politicalpreparedness.usecase.representative.GetRepresentativeUseCase
import com.example.android.politicalpreparedness.usecase.representative.GetStatesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class RepresentativeViewModel(
    private val getStatesUseCase: GetStatesUseCase,
    private val getRepresentativesUseCase: GetRepresentativeUseCase,
) : ViewModel() {

    companion object {
        const val DEFAULT_COUNTRY = "United States"
    }

    private val _representatives = MutableStateFlow<List<Representative>>(listOf())
    val representatives: StateFlow<List<Representative>>
        get() = _representatives.asStateFlow()

    private val _address = MutableStateFlow<Address?>(null)
    val address: StateFlow<Address?>
        get() = _address.asStateFlow()

    @ExperimentalCoroutinesApi
    val country = _address.mapLatest { address ->
        address?.country
    }

    private val _states = MutableStateFlow<List<String>>(listOf())
    val states: StateFlow<List<String>>
        get() = _states.asStateFlow()

    private val _state = MutableStateFlow("")
    val state: StateFlow<String>
        get() = _state.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorState: StateFlow<String>
        get() = _errorMessage

    fun useMyLocation(address: Address?) {
        _address.value = address
    }

    fun setState(state: String?) {
        _state.value = state ?: ""
    }

    fun getCountryState(country: String?) {
        viewModelScope.launch {
            country?.let {
                getStatesUseCase.invoke(it)
                    .collectLatest { result ->
                        if (result is Result.Success) {
                            _states.value = result.data
                            if (state.value != "" && !states.value.contains(state.value)) {
                                addCountryState()
                            }
                        } else if (result is Result.Error) {
                            _errorMessage.value = result.throwable.message.toString()
                        }
                    }
            }
        }
    }

    private fun addCountryState() {
        val states = states.value as ArrayList
        states.add(state.value)
        states.sort()
        _states.value = states
    }

    fun findMyRepresentatives() {
        viewModelScope.launch {
            address.value?.let {
                getRepresentativesUseCase.invoke(it.toFormattedString())
                    .collectLatest { result ->
                        if (result is Result.Success) {
                            _representatives.value = result.data
                        } else if (result is Result.Error) {
                            _errorMessage.value = result.throwable.message.toString()
                        }
                    }
            }
        }
    }

    fun setDefaultStates() {
        getCountryState(DEFAULT_COUNTRY)
    }

    fun onAddress1Changed(address1: CharSequence) {
        _address.value?.line1 = address1.toString()
    }

    fun onAddress2Changed(address2: CharSequence) {
        _address.value?.line2 = address2.toString()
    }

    fun onCityChanged(city: CharSequence) {
        _address.value?.city = city.toString()
    }

    fun onZipChanged(zip: CharSequence) {
        _address.value?.zip = zip.toString()
    }

    fun onStateChanged(position: Int) {
        _state.value = states.value[position]
        _address.value?.state = state.value
    }

    fun resetErrorState() {
        _errorMessage.value = ""
    }
}
