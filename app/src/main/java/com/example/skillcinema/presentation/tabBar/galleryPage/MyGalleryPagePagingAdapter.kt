package com.example.skillcinema.presentation.tabBar.galleryPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.skillcinema.data.dto.EntityItemsPhotoDto
import com.example.skillcinema.databinding.PhotoCardBinding
import com.example.skillcinema.presentation.tabBar.filmpage.MyPhotoMovieViewHolder

class MyGalleryPagePagingAdapter :
    PagingDataAdapter<EntityItemsPhotoDto, MyPhotoMovieViewHolder>(DiffUtilPhotoCallback()) {

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
        val item = getItem(position)

        item?.let {
            Glide.with(holder.binding.imageView.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(holder.binding.imageView)
        }
    }

}

class DiffUtilPhotoCallback : DiffUtil.ItemCallback<EntityItemsPhotoDto>() {
    override fun areItemsTheSame(
        oldItem: EntityItemsPhotoDto,
        newItem: EntityItemsPhotoDto
    ): Boolean = oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(
        oldItem: EntityItemsPhotoDto,
        newItem: EntityItemsPhotoDto
    ): Boolean = oldItem == newItem

}