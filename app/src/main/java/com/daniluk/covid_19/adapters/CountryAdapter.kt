package com.daniluk.covid_19.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniluk.covid_19.CovidViewModel
import com.daniluk.covid_19.R
import com.daniluk.covid_19.pojo.CountryName
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CountryHolder>() {
    var clickCountryListener: ClickCountryListener? = null

    interface ClickCountryListener {
        fun clickToFinish(position: Int)
    }

    var listCountries = listOf<CountryName>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CountryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameCountry = itemView.itemNameCountry
        val itemFlag = itemView.itemFlag
        val context = itemView.context

        init {
            itemView.setOnClickListener {
                clickCountryListener?.clickToFinish(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return CountryHolder(view)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
         var name = listCountries.get(position).nameRU ?: listCountries.get(position).nameEN
         holder.itemNameCountry.text = name

/*
        val flagStr = listCountries.get(position).flagName?.toLowerCase() ?: return
        val reference = holder.context.resources.getIdentifier(flagStr, "drawable", holder.context.packageName)
        holder.itemFlag.setImageDrawable(holder.context.getDrawable(reference))
*/
        val flagStr = listCountries.get(position).flagName?.toLowerCase()
        if (flagStr != null){
            val reference = holder.context.resources.getIdentifier(flagStr, "drawable", holder.context.packageName)
            holder.itemFlag.setImageDrawable(holder.context.getDrawable(reference))
        }else{
            holder.itemFlag.setImageDrawable(null)
        }

    }

    override fun getItemCount() = listCountries.size
}