package com.wrkspot.sampleapp.di

import android.content.Context
import androidx.room.Room
import com.wrkspot.sampleapp.data.dao.CountryDao
import com.wrkspot.sampleapp.data.db.AppDatabase
import com.wrkspot.sampleapp.data.repository.CountriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryDao(appDatabase: AppDatabase): CountryDao {
        return appDatabase.countryDao()
    }

    @Provides
    @Singleton
    fun provideCountriesRepository(countryDao: CountryDao): CountriesRepository {
        return CountriesRepository(countryDao)
    }
}