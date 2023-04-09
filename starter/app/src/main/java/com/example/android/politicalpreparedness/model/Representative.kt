package com.example.android.politicalpreparedness.model

import com.example.android.politicalpreparedness.network.models.Office
import com.example.android.politicalpreparedness.network.models.Official

data class Representative(
    var id: Long = 0L,
    val official: Official,
    val office: Office,
)