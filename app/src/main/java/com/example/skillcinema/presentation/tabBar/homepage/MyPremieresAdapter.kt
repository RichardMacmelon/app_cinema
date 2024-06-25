package com.example.skillcinema.presentation.tabBar.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.databinding.FilmCardBinding

class MyPremieresAdapter : RecyclerView.Adapter<MyPremieresViewHolder>() {

    private var data: List<EntityItemsDto> = emptyList()
//    private var dataFilm: List<EntityFilmDto> = emptyList()

    fun setData(data: List<EntityItemsDto>) {
        this.data = data
        notifyDataSetChanged()
    }

//    fun setFilmData(dataFilm : List<EntityFilmDto>) {
//        this.dataFilm = dataFilm
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int = 8

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPremieresViewHolder {
        return MyPremieresViewHolder(
            FilmCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyPremieresViewHolder, position: Int) {
        val item = data.getOrNull(position)
//        val itemFilm = dataFilm.getOrNull(position)
        with(holder.binding) {
            textViewName.text = item?.nameRu
            textViewGenre.text = item?.genres?.joinToString { it.genre }
            if (item?.ratingKinopoisk != null) {
                textViewRating.text = item.ratingKinopoisk.toString()
            } else {
                textViewRating.text = "-"
            }
//            textViewRating.text = (itemFilm?.ratingKinopoisk ?: "").toString()
            item?.let {
                Glide.with(imageViewBackground.context)
                    .load(it.posterUrlPreview)
                    .into(imageViewBackground)
            }
        }
    }
}

class MyPremieresViewHolder(val binding: FilmCardBinding) : RecyclerView.ViewHolder(binding.root)