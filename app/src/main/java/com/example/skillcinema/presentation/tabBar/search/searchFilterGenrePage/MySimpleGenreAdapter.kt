package com.example.skillcinema.presentation.tabBar.search.searchFilterGenrePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.data.dto.EntityIdGenresDto
import com.example.skillcinema.databinding.CountryAndGenreSampleBinding
import com.example.skillcinema.presentation.tabBar.search.searchFilterCountryPage.MySimpleCountryViewHolder

class MySimpleGenreAdapter(private val onClick: (EntityIdGenresDto) -> Unit) :
    RecyclerView.Adapter<MySimpleCountryViewHolder>() {

    private var data = emptyList<EntityIdGenresDto>()
    fun setData(data: List<EntityIdGenresDto>) {
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
            textView.text = item?.genre
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(it)
            }
        }
    }
}