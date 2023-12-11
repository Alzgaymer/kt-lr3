package com.example.kt_lr_3.domain.factory

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "factories")
data class Factory(
    @PrimaryKey val id: UUID,
    val name: String,
    val numberOfWorkers: Int,
    val numberOfWorkshops: Int,
    val workerSalary: Double,
    val masterSalary: Double,
    val numberOfMasters: Int, // Calculated as 1 master per 10 workers
    val profitPerWorkerPerMonth: Double,
    val profitPerMasterPerMonth: Double
)