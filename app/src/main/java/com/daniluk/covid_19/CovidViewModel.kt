package com.daniluk.covid_19

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daniluk.covid_19.api.ApiFactory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daniluk.covid_19.database.CovidDataBase
import com.daniluk.covid_19.pojo.*
import com.daniluk.covid_19.utils.FlagsCountries
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CovidViewModel(application: Application) : AndroidViewModel(application) {
    val context = application.applicationContext
    val apiService = ApiFactory.apiService
    val database = CovidDataBase.getInstance(context)
    val covidDao = database.covidDao()

    var dataWorld = MutableLiveData<DataCountryFromServer>()
    var dataCurrentCountry = MutableLiveData<DataCountryFromServer>()
    var liveDateAvaliableCoutries = covidDao.getLiveDataNameCountry()
    //var listNameAvaliableCoutries = listOf<CountryName>()

    var flagEndDownloadData = false
    var flagEndDownloadListCountries = false
    var flagEndDownload = MutableLiveData<Boolean>()

    val nameStartCountry = "Ukraine"

    var flagProgressbarDownloadData = false
    var flagProgressbarDownloadListCountries = false
    var progressBar = MutableLiveData<Boolean>(false)

    init {
        Thread {
            dataWorld.postValue(covidDao.getDataWorld())
            dataCurrentCountry.postValue(covidDao.getDataCountry(nameStartCountry))
        }.start()
        startDownload()
    }

    fun startDownload() {
        progressBar.value = true
        getDataFromServer()
        getListNameAvaliableCountriesFromServer()
    }


    fun getDataFromServer() {
        flagProgressbarDownloadData = true
        apiService.getLatestWorld()
                .enqueue(object : Callback<DataWorld> {
                    override fun onResponse(
                            call: Call<DataWorld>,
                            response: Response<DataWorld>
                    ) {
                        val listDataCountryFromServer = response.body()?.listDataCountryFromServer
                        if (listDataCountryFromServer != null) {
                            Thread(Runnable {
                                covidDao.deleteListDataCountry()
                                covidDao.isertListDataCountry(listDataCountryFromServer)
                                flagEndDownloadData = true
                                flagEndDownload.postValue(flagEndDownloadData && flagEndDownloadListCountries)
                                flagProgressbarDownloadData = false
                                progressBar.postValue(flagProgressbarDownloadData || flagProgressbarDownloadListCountries)
                            }).start()
                        }
                    }

                    override fun onFailure(call: Call<DataWorld>, t: Throwable) {
                        Log.d("COVID19", "Ошибка ${t.toString()}")
                        flagProgressbarDownloadData = false
                        progressBar.postValue(flagProgressbarDownloadData || flagProgressbarDownloadListCountries)
                    }
                })
    }

    private fun getListNameAvaliableCountriesFromServer() {
        flagProgressbarDownloadListCountries = true
        apiService.getListCountries()
                .enqueue(object : Callback<ListCountriesFromServer> {
                    override fun onResponse(
                            call: Call<ListCountriesFromServer>,
                            response: Response<ListCountriesFromServer>
                    ) {
                        val listNames = response.body()?.response ?: return
                        val listCountriesFromServer = arrayListOf<CountryName>()
                        for (name in listNames) {
                            val country = CountryName()
                            country.nameEN = name
                            country.nameRU = FlagsCountries.mapFlagsCountries.get(name)?.get(1)
                            country.flagName = FlagsCountries.mapFlagsCountries.get(name)?.get(0)
                            listCountriesFromServer.add(country)
                        }
                        Thread {
                            covidDao.deleteListNameCountry()
                            covidDao.isertListNameCountry(listCountriesFromServer)
                            //listNameAvaliableCoutries = listCountriesFromServer
                            flagEndDownloadListCountries = true
                            flagEndDownload.postValue(flagEndDownloadData && flagEndDownloadListCountries)
                            flagProgressbarDownloadListCountries = false
                            progressBar.postValue(flagProgressbarDownloadData || flagProgressbarDownloadListCountries)
                        }.start()
                    }

                    override fun onFailure(call: Call<ListCountriesFromServer>, t: Throwable) {
                        Log.d("COVID19", "Ошибка ${t.toString()}")
                        flagProgressbarDownloadListCountries = false
                        progressBar.postValue(flagProgressbarDownloadData || flagProgressbarDownloadListCountries)
                    }

                })
    }

    //Дополнить БД - записать русские названия и названия файлов флагов в БД по списку имен доступных стран
    //получить данные по миру dataWorld и по текущей стране dataCurrentCountry из БД
    //сбросить флаги загрузки
    fun writeNameRuEndFlagToDatabase() {
        val listUpdateCountry = arrayListOf<DataCountryFromServer>()
        Thread {
            val listDataCountry = covidDao.getListDataCountry()
            val listNameAvaliableCoutries = covidDao.getListNameCountry()
            for (country in listNameAvaliableCoutries) {
//            val listNameCountry = CovidViewModel.viewModel.liveDateAvaliableCoutries.value ?: return@Thread
//            for(country in listNameCountry){
                val tempList = listDataCountry.filter {
                    it.nameEN == country.nameEN
                }
                if (tempList.size != 1) {
                    continue
                }
                val tempCountry = tempList.get(0)
                tempCountry.nameRU = country.nameRU
                tempCountry.flagName = country.flagName
                listUpdateCountry.add(tempCountry)
            }
            covidDao.updateListDataCountry(listUpdateCountry)

            dataWorld.postValue(covidDao.getDataWorld())
            dataCurrentCountry.postValue(covidDao.getDataCountry(nameStartCountry))
            flagEndDownloadData = false
            flagEndDownloadListCountries = false
            flagEndDownload.postValue(false)

            Log.d("COVID19", "Обновили мир и страну из базы данных")

        }.start()
    }


    companion object {
        lateinit var viewModel: CovidViewModel
    }

}