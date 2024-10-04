package com.example.skillcinema.presentation.tabBar.allPeopleInPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.dto.EntityPeopleDto
import com.example.skillcinema.databinding.PeopleCardForFragmentAllPeopleBinding

class MyAllPeopleCardForAdapter(private val onClick: (EntityPeopleDto) -> Unit) :
    RecyclerView.Adapter<MyAllPeopleCardViewHolder>() {

    private var data: List<EntityPeopleDto> = emptyList()
    fun setData(data: List<EntityPeopleDto>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAllPeopleCardViewHolder {
        return MyAllPeopleCardViewHolder(
            PeopleCardForFragmentAllPeopleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyAllPeopleCardViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {

            if (item != null) {
                textViewNameActor.text = item.nameRu.ifEmpty { item.nameEn }
            }

            textViewDescription.text = item?.description
            item?.let {
                Glide.with(imageViewPhoto.context)
                    .load(it.posterUrl)
                    .into(imageViewPhoto)
            }
        }
        holder.binding.root.setOnClickListener {
            if (item?.professionKey == "ACTOR") {
                item.let { onClick(it) }
            }
        }
    }
}

class MyAllPeopleCardViewHolder(val binding: PeopleCardForFragmentAllPeopleBinding) :
    RecyclerView.ViewHolder(binding.root)