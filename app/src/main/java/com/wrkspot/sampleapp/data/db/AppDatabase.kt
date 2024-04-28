package com.wrkspot.sampleapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wrkspot.sampleapp.data.dao.CountryDao
import com.wrkspot.sampleapp.data.model.CountryDB

@Database(entities = [CountryDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        const val DB_NAME = "countries_list"
    }
}