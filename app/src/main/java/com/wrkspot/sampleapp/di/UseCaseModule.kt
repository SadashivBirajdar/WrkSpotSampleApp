package com.wrkspot.sampleapp.di

import com.wrkspot.sampleapp.data.netwok.client.CountryListApiClient
import com.wrkspot.sampleapp.data.repository.CountriesRepository
import com.wrkspot.sampleapp.domain.GetCountriesFromDBUseCase
import com.wrkspot.sampleapp.domain.GetCountryListUseCase
import com.wrkspot.sampleapp.domain.SaveCountriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object UseCaseModule {

    @Provides
    fun getCountryListUseCase(countryListApiClient: CountryListApiClient): GetCountryListUseCase {
        return GetCountryListUseCase(countryListApiClient)
    }

    @Provides
    fun getSaveCountryUseCase(countriesRepository: CountriesRepository): SaveCountriesUseCase {
        return SaveCountriesUseCase(countriesRepository)
    }

    @Provides
    fun getCountryFromDbUseCase(countriesRepository: CountriesRepository): GetCountriesFromDBUseCase {
        return GetCountriesFromDBUseCase(countriesRepository)
    }

}