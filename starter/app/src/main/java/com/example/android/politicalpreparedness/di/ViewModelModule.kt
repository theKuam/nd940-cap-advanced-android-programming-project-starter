package com.example.android.politicalpreparedness.di

import com.example.android.politicalpreparedness.common.SharedViewModel
import com.example.android.politicalpreparedness.ui.election.ElectionsViewModel
import com.example.android.politicalpreparedness.ui.election.VoterInfoViewModel
import com.example.android.politicalpreparedness.ui.representative.RepresentativeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single {
        SharedViewModel()
    }
    viewModel { RepresentativeViewModel(get(), get()) }
    viewModel { ElectionsViewModel() }
    viewModel { VoterInfoViewModel(get()) }
}