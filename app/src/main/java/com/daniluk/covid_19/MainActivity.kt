package com.daniluk.covid_19

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.daniluk.covid_19.utils.FlagsCountries
import com.daniluk.covid_19.utils.addSpaceToString
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val viewModel:CovidViewModel by viewModels()
    var startPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        Log.d("COVID19", "Старт Активити\n")
        //Сохраняется ссылка на viewModel
        CovidViewModel.viewModel = viewModel

        //Обработчик щелчка для перехода на Activity выбора страны
        cwCountry.setOnClickListener {
            startActivityForResult(ListCountriesActivity.getIntent(this, startPosition), ListCountriesActivity.REQUEST_CODE)
        }

        //Обработчик щелчка для запуска загрузки данных из интернета
        cwWorld.setOnClickListener {
            viewModel.startDownload()
        }


        //Observer на progressBar
        viewModel.progressBar.observe(this, {
            if (it == true){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        })

        //Observer на флаг окончания загрузки с сервера
        viewModel.flagEndDownload.observe(this, {
            Log.d("COVID19", "Изменился флаг flagEndDownload = $it")
            if (it == true) {
                viewModel.writeNameRuEndFlagToDatabase()
            }
        })

        //Observer на данные по миру
        viewModel.dataWorld.observe(this, {
            if (it != null) {
                allCases.text = (it.cases?.totalCases ?: "No data").toString().addSpaceToString().trim()
                activeBadly.text = (it.cases?.activeCases ?: "No data").toString().addSpaceToString().trim()
                newBadly.text = (it.cases?.newCases ?: "No data").toString().addSpaceToString().trim()
                recovered.text = (it.cases?.recoveredCases ?: "No data").toString().addSpaceToString().trim()
                deaths.text = (it.deaths?.totalDeaths ?: "No data").toString().addSpaceToString().trim()
                date.text = (it.day ?: "No data").toString()
            }
        })

        //Observer на текущую страну
        viewModel.dataCurrentCountry.observe(this, {
            if (it != null) {
                allCasesCountry.text = (it.cases?.totalCases ?: "No data").toString().addSpaceToString().trim()
                activeBadlyCountry.text = (it.cases?.activeCases ?: "No data").toString().addSpaceToString().trim()
                newBadlyCountry.text = (it.cases?.newCases ?: "No data").addSpaceToString().trim()
                recoveredCountry.text = (it.cases?.recoveredCases ?: "No data").toString().addSpaceToString().trim()
                deathsCountry.text = (it.deaths?.totalDeaths ?: "No data").toString().addSpaceToString().trim()
                dateCountry.text = it.day ?: "No data"
                populationCountry.text = (it.population ?: "No data").toString().addSpaceToString().trim()
                nameCountry.text = it.nameRU ?: it.nameEN
                //nameCountry.text = FlagsCountries.mapFlagsCountries.get(it.nameEN)?.get(1) ?: it.nameEN


                if (it.nameEN != null) {
                    val flagStr = FlagsCountries.mapFlagsCountries.get(it.nameEN)?.get(0)
                        ?.toLowerCase(Locale.ROOT)
                    if (flagStr != null) {
                        //imageFlag.setImageDrawable(getDrawable(CovidViewModel.getFlagReference(flagStr, this)))
                        imageFlag.setImageDrawable(getDrawable(resources.getIdentifier(flagStr, "drawable", packageName)))
                    }else{
                        imageFlag.setImageDrawable(null)
                    }
                }

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ListCountriesActivity.REQUEST_CODE && resultCode == RESULT_OK){
            val position = data?.getIntExtra(ListCountriesActivity.KEY_START_POSITION, 0)
            startPosition = position ?: 0
        }
    }
}
