package com.daniluk.covid_19.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Cases(
    @SerializedName("new")
    @Expose
    val newCases: String? = null,

    @SerializedName("active")
    @Expose
    val activeCases: Int? = null,

    @SerializedName("critical")
    @Expose
    val criticalCases: Int? = null,

    @SerializedName("recovered")
    @Expose
    val recoveredCases: Int? = null,

    @SerializedName("1M_pop")
    @Expose
    val _1MPopCases: String? = null,

    @SerializedName("total")
    @Expose
    val totalCases: Int? = null

)
