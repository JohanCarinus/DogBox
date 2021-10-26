package com.johancarinus.dogbox.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import johancarinus.dogbox.R
import johancarinus.dogbox.databinding.ImageViewHolderBinding

class MasonryImageGalleryAdapter(
    private val onClickListener: MasonryImageGalleryOnClickListener
) : ListAdapter<Uri, MasonryImageGalleryAdapter.ViewHolder>(UriImageDiffUtil) {

    companion object UriImageDiffUtil : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem.toString() == newItem.toString()
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
            val uri = getItem(position)
            binding.imageCard.setOnClickListener {
                onClickListener.onClick(uri)
            }
            binding.image.load(uri)
        }
    }
}