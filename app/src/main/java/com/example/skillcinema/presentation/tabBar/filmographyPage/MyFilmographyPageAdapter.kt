package com.example.skillcinema.presentation.tabBar.filmographyPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.data.EntityFilmographyDto
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.databinding.FilmographyFilmBinding

class MyFilmographyPageAdapter(private val onClick: (EntityFilmographyDto) -> Unit) :
    RecyclerView.Adapter<MyFilmographyPageViewHolder>() {

    private var data = emptyList<EntityFilmographyDto>()

    fun setData(data: List<EntityFilmographyDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFilmographyPageViewHolder {
        return MyFilmographyPageViewHolder(
            FilmographyFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyFilmographyPageViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {

            var name: String? = item?.nameRu ?: item?.nameEn

            if (name?.length!! >= 30) {
                name = "${name.substring(0, name.length / 2)}..."
            }

            textViewRating.text = item?.rating ?: "-"
            textViewName.text = name
            textViewYearGenre.text = item?.description

        }
        holder.binding.root.setOnClickListener {
            item?.let { onClick(it) }
        }

    }
}

class MyFilmographyPageViewHolder(val binding: FilmographyFilmBinding) :
    RecyclerView.ViewHolder(binding.root)