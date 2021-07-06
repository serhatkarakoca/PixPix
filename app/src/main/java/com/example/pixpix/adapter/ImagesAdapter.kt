package com.example.pixpix.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pixpix.R
import com.example.pixpix.databinding.ItemImageBinding
import com.example.pixpix.model.ImageModel

class ImagesAdapter :
    PagingDataAdapter<ImageModel, ImagesAdapter.ImagesViewHolder>(DiffUtilCallBack()) {

    class ImagesViewHolder(var view: ItemImageBinding) : RecyclerView.ViewHolder(view.root)


    class DiffUtilCallBack : DiffUtil.ItemCallback<ImageModel>() {
        override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.view.image = currentItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemImageBinding>(
            inflater,
            R.layout.item_image,
            parent,
            false
        )
        return ImagesViewHolder(view)
    }
}