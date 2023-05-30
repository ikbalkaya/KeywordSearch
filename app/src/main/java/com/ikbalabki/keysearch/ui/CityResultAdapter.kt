package com.ikbalabki.keysearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ikbalabki.keysearch.R
import com.ikbalabki.keysearch.data.City

class CityResultAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onResultTap:((City)->Unit)? = null
    private val cities = mutableListOf<City>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return CitiesHolder(itemView)
    }
    fun setResults(results: List<City>){
        cities.clear()
        cities.addAll(results)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CitiesHolder).bind(cities.get(position),this.onResultTap)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun clear() {
        cities.clear()
        notifyDataSetChanged()
    }

    class CitiesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: City, onResultTap: ((City) -> Unit)?) {
            this.title.text = "${city.name}, ${city.country}"
            this.subtitle.text = "${city.coord.lat},${city.coord.lon}"
            itemView.setOnClickListener{onResultTap?.let { onResultTap(city)}}
        }

        val title = itemView.findViewById<TextView>(R.id.title_text_view)
        val subtitle = itemView.findViewById<TextView>(R.id.subtitle_text_view)

    }
}