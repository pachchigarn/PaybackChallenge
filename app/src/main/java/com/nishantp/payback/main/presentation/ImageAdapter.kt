package com.nishantp.payback.main.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nishantp.payback.databinding.ListItemImageBinding
import com.nishantp.payback.main.domain.model.Image

class ImageAdapter(private val onItemClick: (Image) -> Unit): ListAdapter<Image, ImageItemViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder {
        return ImageItemViewHolder(listItemImageBinding = ListItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
            onItemClick(currentList[it])
        }
    }

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        holder.bind(image = getItem(position))
    }
}