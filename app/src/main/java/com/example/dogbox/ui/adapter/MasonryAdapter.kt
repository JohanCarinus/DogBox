package com.example.dogbox.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dogbox.R
import com.example.dogbox.databinding.ImageViewHolderBinding

class MasonryAdapter(private val dataSet: List<Uri>) :
    RecyclerView.Adapter<MasonryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_view_holder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.image.load(dataSet[position]) {
                placeholder(R.drawable.placeholder)
                build()
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ImageViewHolderBinding.bind(view)
    }

    override fun getItemCount() = dataSet.size
}