package com.daniluk.covid_19.api

import com.daniluk.covid_19.pojo.DataWorld
import com.daniluk.covid_19.pojo.ListCountriesFromServer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    //список доступных стран
    @GET("countries")
    fun getListCountries(
        @Header(HEADER_RAPIDAPI_KEY) rapidapiKey: String = RAPIDAPI_KEY,
        @Header(HEADER_RAPIDAPI_HOST) rapidapiHost: String = RAPIDAPI_HOST,
    ): Call<ListCountriesFromServer>

    //последние данные по всему миру
    @GET("statistics")
    fun getLatestWorld(
        @Header(HEADER_RAPIDAPI_KEY) rapidapiKey: String = RAPIDAPI_KEY,
        @Header(HEADER_RAPIDAPI_HOST) rapidapiHost: String = RAPIDAPI_HOST,
    ): Call<DataWorld>


    companion object{
        private const val QUERY_PARAM_NAME = "name"

        private const val HEADER_RAPIDAPI_KEY = "x-rapidapi-key"
        private const val RAPIDAPI_KEY = "0db27f9555msh27e33833bf5b0b7p18a2a1jsn4c0a64ce6f99"
        private const val HEADER_RAPIDAPI_HOST = "x-rapidapi-host"
        private const val RAPIDAPI_HOST = "covid-193.p.rapidapi.com"
    }

}