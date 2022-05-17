package com.cr4zyrocket.sapoctl.presenter.compositeitem.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.model.CompositeItem
import com.cr4zyrocket.sapoctl.model.Variant
import java.text.NumberFormat
import java.util.*

class CompositeItemAdapter(
    private val context: Context,
    private val compositeItems: MutableList<CompositeItem>,
    private val compositeSubVariantItems: MutableList<Variant>
) : RecyclerView.Adapter<CompositeItemAdapter.CompositeSubItemViewHolder>() {
    private var compositeItemList = mutableListOf<CompositeItem>()
    private var compositeSubVariantItemList = mutableListOf<Variant>()
    var onItemClickCompositeItem: ((Long, Long) -> Unit)? = null

    init {
        compositeItemList = compositeItems
        compositeSubVariantItemList = compositeSubVariantItems
    }

    inner class CompositeSubItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCompositeSubItemImage: ImageView = itemView.findViewById(R.id.ivCompositeSubItemImage)
        val tvCompositeSubItemQuantity: TextView =
            itemView.findViewById(R.id.tvCompositeSubItemQuantity)
        val tvCompositeSubItemName: TextView =
            itemView.findViewById(R.id.tvCompositeSubItemName)
        val tvCompositeSubItemSKU: TextView =
            itemView.findViewById(R.id.tvCompositeSubItemSKU)
        val tvCompositeSubItemRetailPrice: TextView =
            itemView.findViewById(R.id.tvCompositeSubItemRetailPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompositeSubItemViewHolder {
        return CompositeSubItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_composite_sub_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CompositeSubItemViewHolder, position: Int) {
        val compositeSubItem = compositeItemList[position]
        val image = compositeSubVariantItemList.firstOrNull {
            compositeSubItem.compositeSubItemId == it.variantId
        }?.variantImages?.get(0)
        Glide.with(context).load(image?.imageFullPath)
            .into(holder.ivCompositeSubItemImage)
        holder.apply {
            tvCompositeSubItemName.text = compositeSubItem.compositeSubItemName
            tvCompositeSubItemQuantity.text = compositeSubItem.compositeSubItemQuantity.toString()
            tvCompositeSubItemSKU.text = compositeSubItem.compositeSubItemSKU
            tvCompositeSubItemRetailPrice.text =
                NumberFormat.getInstance(Locale.US).format(compositeSubItem.compositeSubItemPrice)
                    .toString()

            itemView.setOnClickListener {
                onItemClickCompositeItem?.invoke(
                    compositeSubItem.compositeSubItemProductId,
                    compositeSubItem.compositeSubItemId
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return compositeItemList.size
    }
}