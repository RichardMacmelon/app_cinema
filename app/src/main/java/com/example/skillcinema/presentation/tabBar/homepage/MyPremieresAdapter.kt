package com.example.skillcinema.presentation.tabBar.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.dto.EntityItemsDto
import com.example.skillcinema.databinding.FilmCardBinding

class MyPremieresAdapter(private val onClick: (EntityItemsDto) -> Unit) :
    RecyclerView.Adapter<MyPremieresViewHolder>() {

    private var data: List<EntityItemsDto> = emptyList()
    private var filmCardState: List<Boolean> = emptyList()

    fun setData(data: List<EntityItemsDto>, filmCardState: List<Boolean>) {
        this.data = data
        this.filmCardState = filmCardState
        notifyDataSetChanged()
    }

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
        val gradient = filmCardState.getOrNull(position)
        with(holder.binding) {
            textViewName.text = item?.nameRu
            textViewGenre.text = item?.genres?.joinToString { it.genre }

            if (gradient == true) {
                imageViewGradient.visibility = View.VISIBLE
                imageViewEye.visibility = View.VISIBLE
            } else {
                imageViewGradient.visibility = View.GONE
                imageViewEye.visibility = View.GONE
            }

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

class MyPremieresViewHolder(val binding: FilmCardBinding) : RecyclerView.ViewHolder(binding.root)