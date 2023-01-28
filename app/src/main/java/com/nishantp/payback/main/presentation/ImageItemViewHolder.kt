package com.nishantp.payback.main.presentation

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nishantp.payback.R
import com.nishantp.payback.databinding.ListItemImageBinding
import com.nishantp.payback.main.domain.model.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageItemViewHolder(private val listItemImageBinding: ListItemImageBinding, onItemClicked: (Int) -> Unit): RecyclerView.ViewHolder(listItemImageBinding.root) {

    init {
        listItemImageBinding.root.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(image: Image) {
        CoroutineScope(Dispatchers.Main).launch {
            listItemImageBinding.image.layout(0, 0, 0, 0)
            Glide.with(listItemImageBinding.image.context)
                .load(image.previewImageUrl)
                .placeholder(R.drawable.ic_image_loading)
                .into(listItemImageBinding.image)
        }

        listItemImageBinding.tvUserName.text = image.username
        listItemImageBinding.tvTags.text = image.tags
    }

}