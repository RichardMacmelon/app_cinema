package com.example.skillcinema.presentation.tabBar.filmpage.filmBottomDialoguePage

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.databinding.ItemToCollectionDialogBinding

class MyDialogueAdapter(private val onClick: (Boolean, Int) -> Unit) :
    RecyclerView.Adapter<MyDialogueViewHolder>() {

    private var data = emptyList<CollectionDB>()
    private var checkBoxStates = mutableListOf<Boolean>()

    fun setData(data: List<CollectionDB>, checkBoxStates: MutableList<Boolean>) {
        this.data = data
        this.checkBoxStates = checkBoxStates
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDialogueViewHolder {
        return MyDialogueViewHolder(
            ItemToCollectionDialogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyDialogueViewHolder, position: Int) {
        val collection = data.getOrNull(position)

        with(holder.binding) {
            textViewNameCollections.text = collection?.collectionName
            textViewNameCollections.setTypeface(null, Typeface.BOLD)

            textViewQuantity.text = collection?.collectionSize.toString()

            materialCheckBox.setOnCheckedChangeListener(null)
            materialCheckBox.isChecked = checkBoxStates.getOrNull(position) == true

            materialCheckBox.setOnCheckedChangeListener { _, isChecked ->
                collection?.let {
                    checkBoxStates[position] = isChecked
                    onClick(isChecked, collection.collectionId)
                }
            }
        }
    }
}

class MyDialogueViewHolder(val binding: ItemToCollectionDialogBinding) :
    RecyclerView.ViewHolder(binding.root)
