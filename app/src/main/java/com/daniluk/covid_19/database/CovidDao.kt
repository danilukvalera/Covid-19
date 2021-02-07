package com.daniluk.covid_19.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.daniluk.covid_19.pojo.CountryName
import com.daniluk.covid_19.pojo.DataCountryFromServer

@Dao
interface CovidDao {
//****************************База список стран с данными*******************************
    //получить список стран с данными
    //@Query("SELECT * FROM list_data_country ORDER BY nameCountrie")
    @Query("SELECT * FROM list_data_country")
    fun getListDataCountry(): List<DataCountryFromServer>

    //вставить страну с данными  в БД
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun isertDataCountry(dataCountry: DataCountryFromServer)

    //обновить страну с данными  в БД
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDataCountry(dataCountry: DataCountryFromServer)

    //обновить несколько стран с данными  в БД
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateListDataCountry(listDataCountry: List<DataCountryFromServer>)

    //вставить список стран с данными  в БД
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun isertListDataCountry(listDataCountry: List<DataCountryFromServer>)

    //получить общие данные по всему миру
    @Query("SELECT * FROM list_data_country WHERE nameEN = 'All' ")
    fun getDataWorld(): DataCountryFromServer?

    //получить данные по конкретной стране
    @Query("SELECT * FROM list_data_country WHERE nameEN = :nameCountry ")
    fun getDataCountry(nameCountry: String): DataCountryFromServer?

    //удалить список стран с данными
    @Query("DELETE FROM list_data_country")
    fun deleteListDataCountry()



    //************************База список названий стран и флагов*******************
    //получить список стран
    //@Query("SELECT * FROM list_country ORDER BY nameCountrie")
    @Query("SELECT * FROM list_country ORDER BY nameRU")
    fun getLiveDataNameCountry(): LiveData<List<CountryName>>

    //получить список стран
    //@Query("SELECT * FROM list_country ORDER BY nameCountrie")
    @Query("SELECT * FROM list_country")
    fun getListNameCountry(): List<CountryName>

    //вставить список стран в БД
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun isertListNameCountry(listCountryName: List<CountryName>)

    //удалить список стран
    @Query("DELETE FROM list_country")
    fun deleteListNameCountry()
}