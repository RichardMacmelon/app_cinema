package com.example.skillcinema.presentation.tabBar.search.searchFilterCountryPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.data.dto.EntityIdCountriesDto
import com.example.skillcinema.databinding.CountryAndGenreSampleBinding

class MySimpleCountryAdapter(private val onClick: (EntityIdCountriesDto) -> Unit) : RecyclerView.Adapter<MySimpleCountryViewHolder>() {

    private var data = emptyList<EntityIdCountriesDto>()
    fun setData(data: List<EntityIdCountriesDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MySimpleCountryViewHolder {
        return MySimpleCountryViewHolder(
            CountryAndGenreSampleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MySimpleCountryViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            textView.text = item?.country
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(it)
            }
        }
    }
}

class MySimpleCountryViewHolder(val binding: CountryAndGenreSampleBinding) :
    RecyclerView.ViewHolder(binding.root)