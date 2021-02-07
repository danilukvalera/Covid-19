package com.daniluk.covid_19.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "list_country")
data class CountryName(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("nameEN")
    @Expose
    var nameEN: String = "",

    @SerializedName("nameRU")
    @Expose
    var nameRU: String? = null,

    @SerializedName("flagName")
    @Expose
    var flagName: String? = null

)
