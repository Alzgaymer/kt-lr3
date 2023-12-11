package com.example.kt_lr_3.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kt_lr_3.domain.factory.FactoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val factoryRepository: FactoryRepository
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> =
        factoryRepository.getAllFactoriesStream()
            .map{HomeUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState(),
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}