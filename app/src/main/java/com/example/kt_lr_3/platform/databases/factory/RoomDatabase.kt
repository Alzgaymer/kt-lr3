package com.example.kt_lr_3.platform.databases.factory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kt_lr_3.domain.factory.Factory
import com.example.kt_lr_3.domain.factory.FactoryDao

@Database(entities = [Factory::class], version = 1, exportSchema = false)
abstract class FactoryDatabase : RoomDatabase() {
    abstract fun factoryDao(): FactoryDao

    companion object {

        @Volatile
        private var Instance: FactoryDatabase? = null

        fun getDatabase(context: Context): FactoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FactoryDatabase::class.java, "factory_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}