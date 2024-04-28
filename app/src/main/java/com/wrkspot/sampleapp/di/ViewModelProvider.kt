package com.wrkspot.sampleapp.di

import com.wrkspot.sampleapp.domain.GetCountriesFromDBUseCase
import com.wrkspot.sampleapp.domain.GetCountryListUseCase
import com.wrkspot.sampleapp.domain.SaveCountriesUseCase
import com.wrkspot.sampleapp.ui.home.HomeScreenViewModel
import com.wrkspot.sampleapp.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ViewModelProvider {

    @Provides
    fun provideViewModel(
        getCountryListUseCase: GetCountryListUseCase,
        saveCountriesUseCase: SaveCountriesUseCase,
        getCountriesFromDBUseCase: GetCountriesFromDBUseCase,
        networkHelper: NetworkHelper
    ): HomeScreenViewModel {
        return HomeScreenViewModel(
            getCountryListUseCase,
            saveCountriesUseCase,
            getCountriesFromDBUseCase,
            networkHelper
        )
    }
}