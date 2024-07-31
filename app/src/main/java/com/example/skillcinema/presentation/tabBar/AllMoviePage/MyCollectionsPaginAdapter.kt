package com.example.skillcinema.presentation.tabBar.AllMoviePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.databinding.FilmCardBinding
import com.example.skillcinema.presentation.tabBar.homepage.MyPremieresViewHolder

class MyCollectionsPaginAdapter(private val onClick: (EntityItemsDto) -> Unit) :
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
            if (item?.ratingKinopoisk != null) {
                textViewRating.text = item.ratingKinopoisk.toString()
            } else {
                textViewRating.text = "-"
            }
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


class DiffUtilItemCallback : DiffUtil.ItemCallback<EntityItemsDto>() {
    override fun areItemsTheSame(oldItem: EntityItemsDto, newItem: EntityItemsDto): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId


    override fun areContentsTheSame(oldItem: EntityItemsDto, newItem: EntityItemsDto): Boolean =
        oldItem == newItem

}