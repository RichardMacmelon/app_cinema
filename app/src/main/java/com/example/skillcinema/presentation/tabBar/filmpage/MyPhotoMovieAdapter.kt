package com.example.skillcinema.presentation.tabBar.filmpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.EntityItemsPhotoDto
import com.example.skillcinema.databinding.PhotoCardBinding

class MyPhotoMovieAdapter : RecyclerView.Adapter<MyPhotoMovieViewHolder>() {

    private var data: List<EntityItemsPhotoDto> = emptyList()
    fun setData(data: List<EntityItemsPhotoDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPhotoMovieViewHolder {
        return MyPhotoMovieViewHolder(
            PhotoCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyPhotoMovieViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            item?.let {
                Glide.with(imageView.context).load(it.previewUrl).centerCrop().into(imageView)
            }
        }
    }
}

class MyPhotoMovieViewHolder(val binding: PhotoCardBinding) :
    RecyclerView.ViewHolder(binding.root)
