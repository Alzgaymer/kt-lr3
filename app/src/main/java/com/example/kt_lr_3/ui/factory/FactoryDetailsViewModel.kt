package com.example.kt_lr_3.ui.factory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kt_lr_3.domain.factory.FactoryRepository
import kotlinx.coroutines.flow.*
import java.util.*

class FactoryDetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val factoryRepository: FactoryRepository,
): ViewModel() {
    private val factoryId: UUID = UUID.fromString(savedStateHandle[FactoryDetailsDestination.factoryIdArg])

    val uiState: StateFlow<FactoryUiState> =
        factoryRepository.getFactoryStream(factoryId)
            .filterNotNull()
            .map {
                FactoryUiState(factoryDetails = it.toFactoryDetails(), true)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FactoryUiState()
            )

    suspend fun deleteFactory() =
        factoryRepository.deleteFactory(uiState.value.factoryDetails.toFactory())


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}