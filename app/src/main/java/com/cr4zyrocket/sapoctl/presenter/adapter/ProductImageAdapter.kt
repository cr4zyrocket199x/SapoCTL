package com.cr4zyrocket.sapoctl.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.model.Image

class ProductImageAdapter(private val context: Context, images: MutableList<Image>) :
    RecyclerView.Adapter<ProductImageAdapter.PictureViewHolder>() {

    private var imageList = mutableListOf<Image>()

    init {
        imageList = images
    }

    inner class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProductPicture: ImageView = itemView.findViewById(R.id.ivProductImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_picture, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val image = imageList[position]
        Glide.with(context).load(image.imageFullPath).into(holder.ivProductPicture)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}