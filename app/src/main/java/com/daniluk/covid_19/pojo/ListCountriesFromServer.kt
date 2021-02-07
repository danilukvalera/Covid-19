package com.daniluk.covid_19.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class ListCountriesFromServer(

    @SerializedName("response")
    @Expose
    val response: List<String>? = null

)
