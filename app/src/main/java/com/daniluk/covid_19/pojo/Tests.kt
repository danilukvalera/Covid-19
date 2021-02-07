package com.daniluk.covid_19.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Tests(
    @SerializedName("1M_pop")
    @Expose
    val _1MPopTests: String? = null,

    @SerializedName("total")
    @Expose
    val totalTests: Int? = null
)
