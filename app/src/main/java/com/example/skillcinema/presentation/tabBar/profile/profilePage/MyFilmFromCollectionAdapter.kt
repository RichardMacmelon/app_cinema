package com.example.skillcinema.presentation.tabBar.profile.profilePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.tables.FilmDB
import com.example.skillcinema.databinding.FilmCardBinding
import com.example.skillcinema.presentation.tabBar.homepage.MyPremieresViewHolder

class MyFilmFromCollectionAdapter(
    private val onClick: (FilmDB) -> Unit
) : RecyclerView.Adapter<MyPremieresViewHolder>() {

    private var data = emptyList<FilmDB>()
    fun setData(data: List<FilmDB>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (data.size <= 8) {
            data.size
        } else {
            8
        }
    }

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
            textViewName.text = item?.filmName
            textViewGenre.text = item?.filmGenre
            textViewRating.text = item?.filmRating
            item?.let {
                Glide.with(imageViewBackground.context)
                    .load(it.filmPoster)
                    .into(imageViewBackground)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let { onClick(it) }
        }
    }
}