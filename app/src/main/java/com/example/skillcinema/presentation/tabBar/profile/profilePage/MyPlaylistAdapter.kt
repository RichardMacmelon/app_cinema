package com.example.skillcinema.presentation.tabBar.profile.profilePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.data.tables.FilmDB
import com.example.skillcinema.databinding.CollectionsCardBinding

class MyPlaylistAdapter(
    private val onClick: (CollectionDB) -> Unit,
    private val onDelete: (CollectionDB) -> Unit
) :
    RecyclerView.Adapter<MyPlaylistViewHolder>() {

    private var data: List<CollectionDB> = emptyList()

    fun setData(data: List<CollectionDB>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPlaylistViewHolder {
        return MyPlaylistViewHolder(
            CollectionsCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyPlaylistViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            textViewNameCollections.text = item?.collectionName
            textViewRatingFilm.text = item?.collectionSize.toString()

            when (item?.collectionId) {
                3 -> {
                    Glide.with(holder.binding.root).load(R.drawable.button_like)
                        .into(holder.binding.imageViewIconCollection)
                    buttonDelete.visibility = View.GONE
                }

                4 -> {
                    Glide.with(holder.binding.root).load(R.drawable.button_notes)
                        .into(holder.binding.imageViewIconCollection)
                    buttonDelete.visibility = View.GONE
                }

                else -> {
                    Glide.with(holder.binding.root).load(R.drawable.profile_button)
                        .into(holder.binding.imageViewIconCollection)
                }
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let { onClick(it) }
        }
        holder.binding.buttonDelete.setOnClickListener {
            item?.let { onDelete(it) }
            notifyDataSetChanged()
        }
    }

}

class MyPlaylistViewHolder(val binding: CollectionsCardBinding) :
    RecyclerView.ViewHolder(binding.root)