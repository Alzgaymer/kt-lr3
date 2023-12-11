package com.example.kt_lr_3.domain.factory

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FactoryRepository {

    fun getAllFactoriesStream(): Flow<List<Factory>>

    fun getFactoryStream(id: UUID): Flow<Factory?>

    suspend fun insertFactory(factory: Factory)

    suspend fun deleteFactory(factory: Factory)

    suspend fun updateFactory(factory: Factory)

}