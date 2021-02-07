package com.daniluk.covid_19

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniluk.covid_19.adapters.CountryAdapter
import com.daniluk.covid_19.database.CovidDataBase
import kotlinx.android.synthetic.main.activity_list_countries.*

class ListCountriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_countries)
        supportActionBar?.hide()


        val layoutManager = LinearLayoutManager(this)
        rwListCountries.layoutManager = layoutManager
        val startPosition = intent.getIntExtra(KEY_START_POSITION, 0)
        layoutManager.scrollToPosition(startPosition)

        val adapter = CountryAdapter()
        rwListCountries.adapter = adapter

        adapter.clickCountryListener = object : CountryAdapter.ClickCountryListener{
            override fun clickToFinish(position: Int) {
                val country = CovidViewModel.viewModel.liveDateAvaliableCoutries.value?.get(position)
                val name = country?.nameEN ?: return
                Thread {
                    CovidViewModel.viewModel.dataCurrentCountry.postValue(
                        CovidDataBase.getInstance(
                            this@ListCountriesActivity
                        ).covidDao().getDataCountry(name)
                    )
                    val topPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val intent = Intent()
                    intent.putExtra(KEY_START_POSITION, topPosition)
                    setResult(RESULT_OK, intent)
                    finish()
                }.start()

            }

        }



        CovidViewModel.viewModel.liveDateAvaliableCoutries.observe(this, {
            adapter.listCountries = it
        })

    }

    companion object{
        val REQUEST_CODE = 100
        val KEY_START_POSITION = "startPosition"
        fun getIntent(context: Context, startPosition: Int): Intent{
            val intent = Intent(context, ListCountriesActivity::class.java)
            intent.putExtra(KEY_START_POSITION, startPosition)
            return  intent
        }
    }
}