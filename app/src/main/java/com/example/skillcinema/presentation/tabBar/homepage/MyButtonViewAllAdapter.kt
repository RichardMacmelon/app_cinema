package com.example.skillcinema.presentation.tabBar.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.databinding.ButtonViewAllBinding

class MyButtonViewAllAdapter(private val onClick:() -> Unit) : RecyclerView.Adapter<LoadButtonViewHolder>() {


    override fun getItemCount(): Int  = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadButtonViewHolder {
        return LoadButtonViewHolder(
            ButtonViewAllBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LoadButtonViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            run { onClick() }
        }
    }


}

class LoadButtonViewHolder(val binding: ButtonViewAllBinding) :
    RecyclerView.ViewHolder(binding.root)