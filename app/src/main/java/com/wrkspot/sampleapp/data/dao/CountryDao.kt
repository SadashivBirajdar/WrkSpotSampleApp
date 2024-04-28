package com.wrkspot.sampleapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wrkspot.sampleapp.data.model.CountryDB

@Dao
interface CountryDao {

    @Query("SELECT * FROM countrydb")
    suspend fun getAll(): List<CountryDB>

    @Query("SELECT * FROM countrydb WHERE (lower(name) LIKE :startWith || '%')")
    suspend fun findByName(startWith: String): List<CountryDB>

    @Query("SELECT * FROM countrydb WHERE population < :populationCount")
    suspend fun findByPopulation(populationCount: Int): List<CountryDB>

    @Insert
    suspend fun insertAll(countries: List<CountryDB>)
}