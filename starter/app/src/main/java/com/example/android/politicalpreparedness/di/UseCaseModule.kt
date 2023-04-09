package com.example.android.politicalpreparedness.di

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
}