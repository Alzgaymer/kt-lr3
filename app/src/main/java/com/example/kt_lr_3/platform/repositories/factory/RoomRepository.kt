package com.example.kt_lr_3.platform.repositories.factory

import com.example.kt_lr_3.domain.factory.Factory
import com.example.kt_lr_3.domain.factory.FactoryDao
import com.example.kt_lr_3.domain.factory.FactoryRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class RoomRepository(private val factoryDao: FactoryDao): FactoryRepository {
    override fun getAllFactoriesStream(): Flow<List<Factory>> = factoryDao.getAllFactories()

    override fun getFactoryStream(id: UUID): Flow<Factory?> = factoryDao.getFactory(id)

    override suspend fun insertFactory(factory: Factory) = factoryDao.insert(factory)

    override suspend fun deleteFactory(factory: Factory) = factoryDao.delete(factory)

    override suspend fun updateFactory(factory: Factory) = factoryDao.update(factory)
}