package com.daniluk.covid_19.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Deaths(
    @SerializedName("new")
    @Expose
    val newDeaths: String? = null,

    @SerializedName("1M_pop")
    @Expose
    val _1MPopDeaths: String? = null,

    @SerializedName("total")
    @Expose
    val totalDeaths: Int? = null
)
