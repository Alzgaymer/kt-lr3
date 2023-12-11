package com.example.kt_lr_3.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kt_lr_3.FactoryApplication
import com.example.kt_lr_3.ui.factory.FactoryDetailsViewModel
import com.example.kt_lr_3.ui.factory.FactoryEditViewModel
import com.example.kt_lr_3.ui.factory.FactoryEntryViewModel
import com.example.kt_lr_3.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(inventoryApplication().container.itemsRepository)
        }

        initializer {
            FactoryEntryViewModel(inventoryApplication().container.itemsRepository)
        }

        initializer {
            FactoryDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }

        initializer {
            FactoryEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }
    }
}

fun CreationExtras.inventoryApplication(): FactoryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FactoryApplication)
