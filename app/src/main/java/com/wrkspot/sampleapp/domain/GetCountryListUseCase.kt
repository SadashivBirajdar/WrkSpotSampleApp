package com.wrkspot.sampleapp.domain

import com.wrkspot.sampleapp.data.model.ApiResponseCountry
import com.wrkspot.sampleapp.data.netwok.client.CountryListApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCountryListUseCase @Inject constructor(private val countryListApiClient: CountryListApiClient) {

    fun execute(): Flow<Result> {
        return flow {
            emit(Result.Loading)
            val response = countryListApiClient.getCountries()
            if (response.isSuccessful && response.body() != null) {
                emit(Result.Success(response.body()!!))
            } else {
                emit(Result.Failure("Get country api failed"))
            }
        }.catch {
            emit(Result.Failure(it.message ?: "Get country api failed"))
        }
    }

    sealed class Result {
        data object Loading : Result()
        data class Success(val data: List<ApiResponseCountry>) : Result()
        data class Failure(val error: String) : Result()
    }
}