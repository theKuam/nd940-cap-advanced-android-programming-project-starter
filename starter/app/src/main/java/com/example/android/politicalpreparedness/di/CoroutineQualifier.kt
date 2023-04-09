package com.example.android.politicalpreparedness.di

enum class CoroutinesQualifier {
    DefaultDispatcher,
    IoDispatcher,
    MainDispatcher,
    MainImmediateDispatcher,
    ApplicationScope
}