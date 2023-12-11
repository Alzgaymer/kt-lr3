package com.example.kt_lr_3.ui.home

import com.example.kt_lr_3.domain.factory.Factory

data class HomeUiState(
    val factories: List<Factory> = emptyList()
)