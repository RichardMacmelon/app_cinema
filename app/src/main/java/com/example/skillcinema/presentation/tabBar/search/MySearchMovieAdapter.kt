package com.example.skillcinema.presentation.tabBar.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.EntityPeopleDto
import com.example.skillcinema.data.EntitySearchDataMovieDto
import com.example.skillcinema.databinding.FilmCardInSearchBinding

class MySearchMovieAdapter(private val onClick: (EntitySearchDataMovieDto) -> Unit) : RecyclerView.Adapter<MySearchMovieViewHolder>() {

    private var data: List<EntitySearchDataMovieDto> = emptyList()
    fun setData(data: List<EntitySearchDataMovieDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySearchMovieViewHolder {
        return MySearchMovieViewHolder(
            FilmCardInSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MySearchMovieViewHolder, position: Int) {
        val item = data.getOrNull(position)

        with(holder.binding) {
            textViewName.text = item?.nameRu ?: item?.nameEn
            textViewRating.text = if (item?.rating == "null") "-" else item?.rating
            textViewGenre.text = "${ item?.year} ${item?.genres?.joinToString { it.genre }}"

            item?.let {
                Glide.with(imageViewBackground.context)
                    .load(it.posterUrlPreview)
                    .into(imageViewBackground)
            }
            holder.binding.root.setOnClickListener {
                item?.let { onClick(it) }
            }
        }
    }

}


class MySearchMovieViewHolder(val binding: FilmCardInSearchBinding) :
    RecyclerView.ViewHolder(binding.root)