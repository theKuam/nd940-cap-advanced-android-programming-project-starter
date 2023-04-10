package com.example.android.politicalpreparedness.di

import com.example.android.politicalpreparedness.usecase.election.CheckSavedElectionUseCase
import com.example.android.politicalpreparedness.usecase.election.GetSavedElectionsUseCase
import com.example.android.politicalpreparedness.usecase.election.GetUpcomingElectionsUseCase
import com.example.android.politicalpreparedness.usecase.election.GetVoterInfoUseCase
import com.example.android.politicalpreparedness.usecase.election.RemoveElectionUseCase
import com.example.android.politicalpreparedness.usecase.election.SaveElectionUseCase
import com.example.android.politicalpreparedness.usecase.representative.GetRepresentativeUseCase
import com.example.android.politicalpreparedness.usecase.representative.GetStatesUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    single {
        GetStatesUseCase(
            get(),
            get(qualifier = named(CoroutinesQualifier.IoDispatcher))
        )
    }
    single {
        GetRepresentativeUseCase(
            get(),
            get(qualifier = named(CoroutinesQualifier.IoDispatcher))
        )
    }
    single {
        GetUpcomingElectionsUseCase(
            get(),
            get(qualifier = named(CoroutinesQualifier.IoDispatcher))
        )
    }
    single {
        GetSavedElectionsUseCase(
            get(),
            get(qualifier = named(CoroutinesQualifier.IoDispatcher))
        )
    }
    single {
        GetVoterInfoUseCase(
            get(),
            get(qualifier = named(CoroutinesQualifier.IoDispatcher))
        )
    }
    single {
        SaveElectionUseCase(
            get(),
            get(qualifier = named(CoroutinesQualifier.IoDispatcher))
        )
    }
    single {
        CheckSavedElectionUseCase(
            get(),
            get(qualifier = named(CoroutinesQualifier.IoDispatcher))
        )
    }
    single {
        RemoveElectionUseCase(
            get(),
            get(qualifier = named(CoroutinesQualifier.IoDispatcher))
        )
    }
}