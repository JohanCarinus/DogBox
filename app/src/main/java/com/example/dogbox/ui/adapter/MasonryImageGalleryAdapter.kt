package com.example.dogbox.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dogbox.R
import com.example.dogbox.databinding.ImageViewHolderBinding
import com.example.dogbox.model.UriImageData

class MasonryImageGalleryAdapter(private val onClickListener: MasonryImageGalleryOnClickListener) :
    ListAdapter<UriImageData, MasonryImageGalleryAdapter.ViewHolder>(UriImageDiffUtil) {

    companion object UriImageDiffUtil : DiffUtil.ItemCallback<UriImageData>() {
        override fun areItemsTheSame(oldItem: UriImageData, newItem: UriImageData): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: UriImageData, newItem: UriImageData): Boolean {
            return oldItem.isContentEqualTo(newItem)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ImageViewHolderBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_view_holder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val uriImage = getItem(position)
            binding.imageCard.setOnClickListener {
                onClickListener.onClick(uriImage.uri)
            }
            binding.image.load(uriImage.uri) {
                placeholder(uriImage.placeholderRes)
                build()
            }
        }
    }
}