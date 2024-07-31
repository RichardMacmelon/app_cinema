package com.example.skillcinema.presentation.tabBar.filmpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.data.EntityItemsSimilarsFilmsDto
import com.example.skillcinema.data.EntityPeopleDto
import com.example.skillcinema.databinding.PeopleCardBinding

class MyPeopleCardAdapter(private val onClick: (EntityPeopleDto) -> Unit) :
    RecyclerView.Adapter<MyPeopleCardViewHolder>() {

    private var data: List<EntityPeopleDto> = emptyList()
    fun setData(data: List<EntityPeopleDto>) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPeopleCardViewHolder {
        return MyPeopleCardViewHolder(
            PeopleCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyPeopleCardViewHolder, position: Int) {
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


class MyPeopleCardViewHolder(val binding: PeopleCardBinding) : RecyclerView.ViewHolder(binding.root)