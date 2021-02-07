package com.daniluk.covid_19.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.daniluk.covid_19.pojo.CountryName
import com.daniluk.covid_19.pojo.DataCountryFromServer

@Database(entities = [DataCountryFromServer::class, CountryName::class], version = 1, exportSchema = false)
abstract class CovidDataBase: RoomDatabase() {
    companion object {
        private var db: CovidDataBase? = null
        private const val DB_NAME = "covid.db"

        fun getInstance(context: Context): CovidDataBase {
            synchronized(this) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        CovidDataBase::class.java,
                        DB_NAME
                    ).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun covidDao(): CovidDao
}