package com.example.kt_lr_3.ui.factory

import com.example.kt_lr_3.domain.factory.Factory
import java.util.*

data class FactoryDetails (
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val numberOfWorkers: String = "",
    val numberOfWorkshops: String = "",
    val numberOfMasters: String = "", // Calculated as 1 master per 10 workers
    val workerSalary: String = "",
    val masterSalary: String = "",
    val profitPerWorkerPerMonth: String = "",
    val profitPerMasterPerMonth: String = ""
)

fun FactoryDetails.toFactory(): Factory = Factory(
    id = id,
    name = name,
    numberOfWorkers = numberOfWorkers.toIntOrNull() ?: 0,
    numberOfWorkshops = numberOfWorkshops.toIntOrNull() ?: 0,
    workerSalary = workerSalary.toDoubleOrNull() ?: 0.0,
    masterSalary = workerSalary.toDoubleOrNull() ?: 0.0,
    numberOfMasters = numberOfMasters.toIntOrNull() ?: 0,
    profitPerWorkerPerMonth = profitPerWorkerPerMonth.toDoubleOrNull() ?: 0.0,
    profitPerMasterPerMonth = profitPerMasterPerMonth.toDoubleOrNull() ?: 0.0
)

fun Factory.toFactoryDetails(): FactoryDetails {
    return FactoryDetails(
        id = this.id,
        name = this.name,
        numberOfWorkers = this.numberOfWorkers.toString(),
        numberOfWorkshops = this.numberOfWorkshops.toString(),
        numberOfMasters =this.numberOfWorkers.toString(),
        workerSalary = this.workerSalary.toString(),
        masterSalary = this.masterSalary.toString(),
        profitPerWorkerPerMonth = this.profitPerWorkerPerMonth.toString(),
        profitPerMasterPerMonth = this.profitPerMasterPerMonth.toString()
    )
}