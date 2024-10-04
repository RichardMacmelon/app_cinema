package com.example.skillcinema.presentation.tabBar.search.SearchFilterYearPage

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.databinding.YearCardBinding

class MySimpleYearAdapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<MySimpleYearViewHolder>() {

    private var data = emptyList<Int>()
    fun setData(data: List<Int>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySimpleYearViewHolder {
        return MySimpleYearViewHolder(
            YearCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private var selectedPosition: Int? = null

    override fun onBindViewHolder(holder: MySimpleYearViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            textViewYear.text = item.toString()
            textViewYear.setTypeface(null, Typeface.BOLD)

            if (position == selectedPosition) {
                textViewYear.setTextColor(Color.BLUE)
            } else {
                textViewYear.setTextColor(Color.BLACK)
            }
        }

        holder.itemView.setOnClickListener {
            val currentPosition = holder.adapterPosition

            if (currentPosition == RecyclerView.NO_POSITION) return@setOnClickListener

            val previousSelectedPosition = selectedPosition
            selectedPosition = currentPosition

            notifyItemChanged(previousSelectedPosition ?: RecyclerView.NO_POSITION)
            notifyItemChanged(selectedPosition ?: RecyclerView.NO_POSITION)

            if (item != null) {
                onClick(item)
            }
        }
    }
}

class MySimpleYearViewHolder(val binding: YearCardBinding) : RecyclerView.ViewHolder(binding.root)