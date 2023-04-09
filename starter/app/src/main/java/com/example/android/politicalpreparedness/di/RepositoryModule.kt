package com.example.android.politicalpreparedness.di

import com.example.android.politicalpreparedness.repositiory.RepresentativeRepository
import com.example.android.politicalpreparedness.repositiory.RepresentativeRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single {
        RepresentativeRepositoryImpl(
            get(),
            get(),
            get(qualifier = named(CoroutinesQualifier.IoDispatcher)),
        )
    } bind RepresentativeRepository::class
}