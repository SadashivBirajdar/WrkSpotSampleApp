package com.wrkspot.sampleapp.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponseCountry(
    @SerializedName("abbreviation")
    val abbreviation: String,
    @SerializedName("capital")
    val capital: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("media")
    val media: Media,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("population")
    val population: Int
) {
    data class Media(
        @SerializedName("emblem")
        val emblem: String,
        @SerializedName("flag")
        val flag: String,
        @SerializedName("orthographic")
        val orthographic: String
    )
}

