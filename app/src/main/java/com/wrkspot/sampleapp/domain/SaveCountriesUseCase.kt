package com.wrkspot.sampleapp.domain

import com.wrkspot.sampleapp.data.model.ApiResponseCountry
import com.wrkspot.sampleapp.data.model.CountryDB
import com.wrkspot.sampleapp.data.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveCountriesUseCase @Inject constructor(private val countriesRepository: CountriesRepository) {

    fun execute(countries: List<ApiResponseCountry>): Flow<Result> {
        return flow {
            try {
                val mappedDbList = countries.map {
                    CountryDB(
                        it.id,
                        it.media.flag,
                        it.name,
                        it.capital,
                        it.currency,
                        it.population
                    )
                }
                countriesRepository.saveCountries(mappedDbList)
                emit(Result.Success)
            } catch (e: Exception) {
                emit(Result.Failure(e.message ?: "Failed to save to database"))
            }
        }.catch {
            emit(Result.Failure(it.message ?: "Failed to save to database"))
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Failure(val error: String) : Result()
    }
}