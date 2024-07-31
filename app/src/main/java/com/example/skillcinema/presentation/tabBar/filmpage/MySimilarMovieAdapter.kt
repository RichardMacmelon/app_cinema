package com.example.skillcinema.presentation.tabBar.filmpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.data.EntityItemsSimilarsFilmsDto
import com.example.skillcinema.databinding.FilmCardBinding
import com.example.skillcinema.presentation.tabBar.homepage.MyPremieresViewHolder

class MySimilarMovieAdapter(private val onClick: (EntityItemsSimilarsFilmsDto) -> Unit) :
    RecyclerView.Adapter<MyPremieresViewHolder>() {

    private var data: List<EntityItemsSimilarsFilmsDto> = emptyList()

    suspend fun setData(data: List<EntityItemsSimilarsFilmsDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

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
        with(holder.binding) {
            textViewName.text = item?.nameRu
            textViewGenre.isGone = true
            imageViewRatingFilm.isGone = true
            textViewRating.isGone = true
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