package com.example.skillcinema.presentation.tabBar.search.searchResultPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.skillcinema.data.dto.EntityItemsMoviesForFiltersDto
import com.example.skillcinema.databinding.FilmCardBinding
import com.example.skillcinema.presentation.tabBar.homepage.MyPremieresViewHolder

class MyResultPagingAdapter(private val onClick: (EntityItemsMoviesForFiltersDto) -> Unit) :
    PagingDataAdapter<EntityItemsMoviesForFiltersDto, MyPremieresViewHolder>(
        DiffUtilItemCallbackResult()
    ) {

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
            textViewGenre.text = item?.genres?.joinToString { it.genre.toString() }
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

class DiffUtilItemCallbackResult() : DiffUtil.ItemCallback<EntityItemsMoviesForFiltersDto>() {
    override fun areItemsTheSame(
        oldItem: EntityItemsMoviesForFiltersDto, newItem: EntityItemsMoviesForFiltersDto
    ): Boolean = oldItem.kinopoiskId == newItem.kinopoiskId


    override fun areContentsTheSame(
        oldItem: EntityItemsMoviesForFiltersDto,
        newItem: EntityItemsMoviesForFiltersDto
    ): Boolean = oldItem == newItem


}