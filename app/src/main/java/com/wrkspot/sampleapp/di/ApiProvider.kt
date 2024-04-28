package com.wrkspot.sampleapp.di

import com.wrkspot.sampleapp.data.netwok.ApiManager
import com.wrkspot.sampleapp.data.netwok.client.CountryListApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiProvider {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return ApiManager.getRetrofit()
    }

    @Provides
    @Singleton
    fun provideCountryListApiClient(retrofit: Retrofit): CountryListApiClient {
        return CountryListApiClient(retrofit.create(CountryListApiClient.CountryApi::class.java))
    }

}