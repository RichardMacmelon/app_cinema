package com.example.skillcinema.presentation.tabBar.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.databinding.FilmCardBinding
import com.example.skillcinema.presentation.MainActivity

//private val onClick: (EntityItemsDto) -> Unit
class MyCollectionsPaginAdapter() :
    PagingDataAdapter<EntityItemsDto, MyPremieresViewHolder>(DiffUtilItemCallback()) {

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
        val item = getItem(position)
        with(holder.binding) {
            textViewName.text = item?.nameRu
            textViewGenre.text = item?.genres?.joinToString { it.genre }
            textViewRating.text = item?.ratingKinopoisk.toString()
            item?.let {
                Glide.with(imageViewBackground.context)
                    .load(it.posterUrlPreview)
                    .into(imageViewBackground)
            }
        }
    }

}


class DiffUtilItemCallback : DiffUtil.ItemCallback<EntityItemsDto>() {
    override fun areItemsTheSame(oldItem: EntityItemsDto, newItem: EntityItemsDto): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId


    override fun areContentsTheSame(oldItem: EntityItemsDto, newItem: EntityItemsDto): Boolean =
        oldItem == newItem

}