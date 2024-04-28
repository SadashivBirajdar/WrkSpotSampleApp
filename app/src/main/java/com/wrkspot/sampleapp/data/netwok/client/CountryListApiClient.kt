package com.wrkspot.sampleapp.data.netwok.client

import com.wrkspot.sampleapp.data.model.ApiResponseCountry
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

/**
 * Retrofit service for api to get countries list
 * */
class CountryListApiClient @Inject constructor(private val countryApi: CountryApi) {

    interface CountryApi {
        @GET("countries/")
        suspend fun getCountries(): Response<List<ApiResponseCountry>>
    }

    suspend fun getCountries():
            Response<List<ApiResponseCountry>> {
        return countryApi.getCountries()
    }
}