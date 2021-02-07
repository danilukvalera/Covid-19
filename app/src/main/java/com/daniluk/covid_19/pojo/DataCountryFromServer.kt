package com.daniluk.covid_19.pojo

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "list_data_country")
data class DataCountryFromServer(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("continent")
    @Expose
    val continent: String? = null,

    @SerializedName("country")
    @Expose
    val nameEN: String? = null,
//***************************************
    @SerializedName("nameRU")
    @Expose
    var nameRU: String? = null,

    @SerializedName("flagName")
    @Expose
    var flagName: String? = null,
//***************************************
    @SerializedName("population")
    @Expose
    val population: Int? = null,

    @SerializedName("cases")
    @Expose
    @Embedded
    val cases: Cases? = null,

    @SerializedName("deaths")
    @Expose
    @Embedded
    val deaths: Deaths? = null,

    @SerializedName("tests")
    @Expose
    @Embedded
    val tests: Tests? = null,

    @SerializedName("day")
    @Expose
    val day: String? = null,

    @SerializedName("time")
    @Expose
    val time: String? = null

)