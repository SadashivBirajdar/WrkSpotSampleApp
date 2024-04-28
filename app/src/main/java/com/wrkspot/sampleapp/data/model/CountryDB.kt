package com.wrkspot.sampleapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryDB(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "image_url") val countryFlagLogo: String,
    @ColumnInfo(name = "name") val countryName: String,
    @ColumnInfo(name = "capital") val countryCapital: String,
    @ColumnInfo(name = "currency") val countryCurrency: String,
    @ColumnInfo(name = "population") val countryPopulation: Int
)
