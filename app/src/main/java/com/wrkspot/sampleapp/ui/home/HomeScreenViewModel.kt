package com.wrkspot.sampleapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wrkspot.sampleapp.data.model.ApiResponseCountry
import com.wrkspot.sampleapp.data.model.Country
import com.wrkspot.sampleapp.domain.GetCountriesFromDBUseCase
import com.wrkspot.sampleapp.domain.GetCountryListUseCase
import com.wrkspot.sampleapp.domain.SaveCountriesUseCase
import com.wrkspot.sampleapp.utils.NetworkHelper
import com.wrkspot.sampleapp.utils.Population
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val countryListUseCase: GetCountryListUseCase,
    private val saveCountriesUseCase: SaveCountriesUseCase,
    private val countriesFromDBUseCase: GetCountriesFromDBUseCase,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _countryListData = MutableLiveData<Result>()
    val countryListData = _countryListData
    private var isDatabaseHasEntries: Boolean? = null
    private var isFetchFromApiInProgress = false


    fun updateCountriesList() {
        if (isFetchFromApiInProgress) {
            return
        }
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                countryListUseCase.execute().collectLatest {
                    if (it is GetCountryListUseCase.Result.Success) {
                        saveCountriesUseCase.execute(it.data).collectLatest {}
                    }
                }
            }
        }
    }

    /**
     * get country list using network call
     * */
    fun getCountries() {
        viewModelScope.launch {

            if (isDatabaseHasEntries == null) {
                isDatabaseHasEntries = checkDbEntries()
            }

            if (isDatabaseHasEntries!!) {
                getAllCountriesFromDb()
            } else if (!networkHelper.isNetworkConnected()) {
                _countryListData.value = Result.Failure("Network not available")
            } else {
                isFetchFromApiInProgress = true
                countryListUseCase.execute().collectLatest { result ->
                    when (result) {
                        GetCountryListUseCase.Result.Loading -> {
                            _countryListData.value = Result.Loading
                        }

                        is GetCountryListUseCase.Result.Failure -> {
                            isFetchFromApiInProgress = false
                            _countryListData.value = Result.Failure(result.error)
                        }

                        is GetCountryListUseCase.Result.Success -> {
                            saveToDatabase(result.data)
                            val mappedData = result.data.map {
                                Country(
                                    it.media.flag,
                                    it.name,
                                    it.capital,
                                    it.currency,
                                    it.population
                                )
                            }
                            isFetchFromApiInProgress = false
                            _countryListData.value = Result.Success(mappedData)
                        }
                    }
                }
            }
        }
    }

    private suspend fun checkDbEntries(): Boolean {
        return countriesFromDBUseCase.isDbEntriesAvailable()
    }

    private fun getAllCountriesFromDb() {
        viewModelScope.launch {
            countriesFromDBUseCase.getAllCountries().collect { result ->
                when (result) {
                    is GetCountriesFromDBUseCase.Result.Failure -> {
                        // handle failure
                    }

                    is GetCountriesFromDBUseCase.Result.Success -> {
                        val mappedData = result.data.map {
                            Country(
                                it.countryFlagLogo,
                                it.countryName,
                                it.countryCapital,
                                it.countryCurrency,
                                it.countryPopulation
                            )
                        }
                        _countryListData.value = Result.Success(mappedData)
                    }
                }
            }
        }
    }

    private fun saveToDatabase(data: List<ApiResponseCountry>) {
        viewModelScope.launch {
            saveCountriesUseCase.execute(data).collectLatest {
                when (it) {
                    is SaveCountriesUseCase.Result.Failure -> {
                        // handle failure
                    }

                    SaveCountriesUseCase.Result.Success -> {
                        isDatabaseHasEntries = true
                    }
                }
            }
        }
    }

    fun getFilteredCountries(population: Population) {
        viewModelScope.launch {
            if (population == Population.None) {
                getAllCountriesFromDb()
            } else {
                countriesFromDBUseCase.getFilteredCountries(population.population)
                    .collect { result ->
                        when (result) {
                            is GetCountriesFromDBUseCase.Result.Failure -> {
                                // handle failure
                            }

                            is GetCountriesFromDBUseCase.Result.Success -> {
                                val mappedData = result.data.map {
                                    Country(
                                        it.countryFlagLogo,
                                        it.countryName,
                                        it.countryCapital,
                                        it.countryCurrency,
                                        it.countryPopulation
                                    )
                                }
                                _countryListData.value = Result.Success(mappedData)
                            }
                        }
                    }
            }
        }
    }

    sealed class Result {
        data object Loading : Result()
        class Success(val data: List<Country>) : Result()
        class Failure(val error: String) : Result()
    }
}