package com.daniluk.covid_19.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataWorld(
    @SerializedName("response")
    @Expose
    val listDataCountryFromServer: List<DataCountryFromServer>? = null
)
