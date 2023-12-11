package com.example.kt_lr_3.ui.factory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kt_lr_3.domain.factory.FactoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class FactoryEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val factoryRepository: FactoryRepository,
) : ViewModel() {
    private val factoryId: UUID = UUID.fromString(savedStateHandle[FactoryEditDestination.factoryIdArg])

    val uiState = MutableStateFlow(FactoryUiState())


    init {
        viewModelScope.launch {
            uiState.value = factoryRepository.getFactoryStream(factoryId)
                .filterNotNull()
                .map {
                    FactoryUiState(factoryDetails = it.toFactoryDetails(), isEntryValid = true)
                }
                .first()
        }
    }

    suspend fun updateFactory() {
        if (validateInput(uiState.value.factoryDetails))
            factoryRepository.updateFactory(uiState.value.factoryDetails.toFactory())
    }

    fun updateUiState(factoryDetails: FactoryDetails) {
        uiState.value =
            FactoryUiState(factoryDetails = factoryDetails, isEntryValid = true)
    }

    private fun validateInput(uiState: FactoryDetails): Boolean {
        return with(uiState) {
            id.toString().isNotBlank() &&
                    name.isNotBlank() &&
                    numberOfWorkers.isNotBlank() &&
                    numberOfWorkshops.isNotBlank() &&
                    numberOfMasters.isNotBlank() &&
                    workerSalary.isNotBlank() &&
                    masterSalary.isNotBlank() &&
                    profitPerWorkerPerMonth.isNotBlank() &&
                    profitPerMasterPerMonth.isNotBlank()
        }
    }

}