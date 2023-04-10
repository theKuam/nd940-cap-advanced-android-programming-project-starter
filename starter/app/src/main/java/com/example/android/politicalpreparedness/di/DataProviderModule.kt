package com.example.android.politicalpreparedness.di

import android.content.Context
import com.example.android.politicalpreparedness.database.ElectionDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataProviderModule = module {
    single { provideDatabase(androidContext()) }
}

fun provideDatabase(context: Context): ElectionDatabase =
    ElectionDatabase.buildDatabase(context)