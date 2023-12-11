package com.example.kt_lr_3.ui.factory

import androidx.lifecycle.ViewModel
import com.example.kt_lr_3.domain.factory.FactoryRepository
import kotlinx.coroutines.flow.MutableStateFlow

class FactoryEntryViewModel(
    private val factoryRepository: FactoryRepository
): ViewModel() {
    val uiState: MutableStateFlow<FactoryUiState> = MutableStateFlow(FactoryUiState())


    fun updateUiState(factoryDetails: FactoryDetails) {
        uiState.value =
            FactoryUiState(factoryDetails = factoryDetails, isEntryValid = validateInput(factoryDetails))
    }

    suspend fun saveFactory() {
        if(validateInput(uiState.value.factoryDetails))
            factoryRepository.insertFactory(uiState.value.factoryDetails.toFactory())
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



