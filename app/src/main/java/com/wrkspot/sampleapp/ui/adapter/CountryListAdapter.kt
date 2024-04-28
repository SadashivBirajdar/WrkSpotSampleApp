package com.wrkspot.sampleapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wrkspot.sampleapp.R
import com.wrkspot.sampleapp.data.model.Country
import com.wrkspot.sampleapp.databinding.ListItemBinding

class CountryListAdapter : RecyclerView.Adapter<CountryListAdapter.ItemViewHolder>(), Filterable {

    private var mCountryList: List<Country> = ArrayList()
    private var mCountryListFiltered: List<Country> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapterData(countryList: List<Country>) {
        mCountryList = countryList
        mCountryListFiltered = mCountryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mCountryListFiltered.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val country = mCountryListFiltered[position]
        holder.binding.apply {
            countryName.text = country.countryName

            countryCapital.text = country.countryCapital
            countryCurrency.text = country.countryCurrency
            countryPopulation.text = country.countryPopulation.toString()

            val defaultOptions: RequestOptions = RequestOptions().error(R.mipmap.ic_launcher_round)
            Glide.with(countryLogo.context).setDefaultRequestOptions(defaultOptions)
                .load(country.countryFlagLogo).into(countryLogo)
        }
    }

    inner class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                mCountryListFiltered = if (charString.isEmpty()) mCountryList else {
                    val filteredList = ArrayList<Country>()
                    mCountryList.filter {
                        (it.countryName.startsWith(constraint!!, true))
                    }.forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply { values = mCountryListFiltered }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mCountryListFiltered =
                    if (results?.values == null) ArrayList() else results.values as List<Country>
                notifyDataSetChanged()
            }
        }
    }
}