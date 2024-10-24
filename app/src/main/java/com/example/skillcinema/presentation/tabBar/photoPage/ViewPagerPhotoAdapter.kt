package com.example.skillcinema.presentation.tabBar.photoPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.dto.EntityItemsPhotoDto
import com.example.skillcinema.databinding.BigPhotoCardBinding
import com.example.skillcinema.presentation.tabBar.galleryPage.DiffUtilPhotoCallback

class ViewPagerPhotoAdapter : PagingDataAdapter<EntityItemsPhotoDto, ViewPagerPhotoViewHolder>(DiffUtilPhotoCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerPhotoViewHolder {
        return ViewPagerPhotoViewHolder(
            BigPhotoCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewPagerPhotoViewHolder, position: Int) {
        val item = getItem(position)

        item?.let {
            Glide.with(holder.binding.imageView.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(holder.binding.imageView)
        }
    }
}

class ViewPagerPhotoViewHolder(val binding: BigPhotoCardBinding) :
    RecyclerView.ViewHolder(binding.root)