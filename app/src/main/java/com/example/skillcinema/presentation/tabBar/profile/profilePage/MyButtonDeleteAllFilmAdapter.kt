package com.example.skillcinema.presentation.tabBar.profile.profilePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.databinding.ButtonDeleteAllBinding

class MyButtonDeleteAllFilmAdapter(private val onClick: () -> Unit) :
    RecyclerView.Adapter<MyButtonDeleteAllFilmViewHolder>() {

    override fun getItemCount(): Int = 1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyButtonDeleteAllFilmViewHolder {
        return MyButtonDeleteAllFilmViewHolder(
            ButtonDeleteAllBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyButtonDeleteAllFilmViewHolder, position: Int) {
        holder.binding.buttonViewAll.setOnClickListener {
            run { onClick() }
        }
    }
}

class MyButtonDeleteAllFilmViewHolder(val binding: ButtonDeleteAllBinding) :
    RecyclerView.ViewHolder(binding.root)