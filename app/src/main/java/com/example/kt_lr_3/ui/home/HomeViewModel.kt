package com.example.kt_lr_3.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kt_lr_3.domain.factory.FactoryRepository
import kotlinx.coroutines.flow.*

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
            ).also {stateFlow ->
                stateFlow.onEach { state ->
                    // Add logging here using the 'state' object
                    Log.d("FactoryViewModel", "New state: $state")
                }.launchIn(viewModelScope)
            }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}