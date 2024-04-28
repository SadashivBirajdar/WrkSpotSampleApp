package com.wrkspot.sampleapp.domain

import com.wrkspot.sampleapp.data.model.Country
import com.wrkspot.sampleapp.data.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCountriesFromDBUseCase @Inject constructor(private val countriesRepository: CountriesRepository) {

    fun getAllCountries(): Flow<Result> {
        return flow {
            try {
                val data = countriesRepository.getAllCountries()
                val mappedData = data.map {
                    Country(
                        it.countryFlagLogo,
                        it.countryName,
                        it.countryCapital,
                        it.countryCurrency,
                        it.countryPopulation
                    )
                }
                emit(Result.Success(mappedData))
            } catch (e: Exception) {
                emit(Result.Failure(e.message ?: "Failed to fetch data from database"))
            }
        }.catch {
            emit(Result.Failure(it.message ?: "Failed to fetch data from database"))
        }
    }

    suspend fun isDbEntriesAvailable(): Boolean {
        return countriesRepository.getAllCountries().isNotEmpty()
    }

    fun getFilteredCountries(population: Int): Flow<Result> {
        return flow {
            try {
                val data = countriesRepository.getCountriesByPopulation(population)
                val mappedData = data.map {
                    Country(
                        it.countryFlagLogo,
                        it.countryName,
                        it.countryCapital,
                        it.countryCurrency,
                        it.countryPopulation
                    )
                }
                emit(Result.Success(mappedData))
            } catch (e: Exception) {
                emit(Result.Failure(e.message ?: "Failed to fetch data from database"))
            }
        }.catch {
            emit(Result.Failure(it.message ?: "Failed to fetch data from database"))
        }
    }

    sealed class Result {
        data class Success(val data: List<Country>) : Result()
        data class Failure(val error: String) : Result()
    }
}