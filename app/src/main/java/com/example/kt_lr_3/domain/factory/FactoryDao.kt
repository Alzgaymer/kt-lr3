package com.example.kt_lr_3.domain.factory

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface FactoryDao {

    @Query("select * from factories order by id asc")
    fun getAllFactories(): Flow<List<Factory>>

    @Query("select * from factories where id = :id")
    fun getFactory(id: UUID): Flow<Factory>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(factory: Factory)

    @Update
    suspend fun update(factory: Factory)

    @Delete
    suspend fun delete(factory: Factory)
}