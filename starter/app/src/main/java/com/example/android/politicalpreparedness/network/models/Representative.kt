package com.example.android.politicalpreparedness.network.models

data class Representative(
    var id: Long = 0L,
    val official: Official,
    val office: Office,
)