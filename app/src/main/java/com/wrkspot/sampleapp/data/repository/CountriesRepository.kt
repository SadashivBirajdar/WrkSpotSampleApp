package com.wrkspot.sampleapp.data.repository

import com.wrkspot.sampleapp.data.dao.CountryDao
import com.wrkspot.sampleapp.data.model.CountryDB
import javax.inject.Inject

class CountriesRepository @Inject constructor(private val countryDao: CountryDao) {

    suspend fun getAllCountries(): List<CountryDB> {
        return countryDao.getAll()
    }

    suspend fun saveCountries(countries: List<CountryDB>) {
        countryDao.insertAll(countries)
    }

    suspend fun getCountriesByName(startWith: String): List<CountryDB> {
        return countryDao.findByName(startWith)
    }

    suspend fun getCountriesByPopulation(population: Int): List<CountryDB> {
        return countryDao.findByPopulation(population)
    }
}