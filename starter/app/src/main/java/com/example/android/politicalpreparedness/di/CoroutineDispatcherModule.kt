package com.example.android.politicalpreparedness.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutinesDispatchersModule = module {
    single(named(CoroutinesQualifier.DefaultDispatcher)) { providesDefaultDispatcher() }
    single(named(CoroutinesQualifier.IoDispatcher)) { providesIoDispatcher() }
    single(named(CoroutinesQualifier.MainDispatcher)) { providesMainDispatcher() }
    single(named(CoroutinesQualifier.MainImmediateDispatcher)) { providesMainImmediateDispatcher() }
}

internal fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

internal fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

internal fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

internal fun providesMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate