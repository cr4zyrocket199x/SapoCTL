package com.cr4zyrocket.sapoctl.presenter.productdetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.model.Variant
import java.text.NumberFormat
import java.util.*

class VariantForOneAdapter(
    private val context: Context,
    variants: MutableList<Variant>
) :
    RecyclerView.Adapter<VariantForOneAdapter.VariantViewHolder>() {
    private var variantList = mutableListOf<Variant>()
    var onItemClickVariantForOne: ((Long, Long) -> Unit)? = null

    init {
        variantList = variants
    }

    inner class VariantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArrowDownRight: ImageView = itemView.findViewById(R.id.ivArrowDownRight)
        val ivVariantImage: ImageView = itemView.findViewById(R.id.ivVariantImage)
        val tvVariantName: TextView = itemView.findViewById(R.id.tvVariantName)
        val tvVariantSKU: TextView = itemView.findViewById(R.id.tvVariantSKU)
        val tvVariantRetailPrice: TextView = itemView.findViewById(R.id.tvVariantRetailPrice)
        val tvVariantOnHand: TextView = itemView.findViewById(R.id.tvVariantOnHand)
        val tvVariantAvailable: TextView = itemView.findViewById(R.id.tvVariantAvailable)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantViewHolder {
        return VariantViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_variant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VariantViewHolder, position: Int) {
        val variant = variantList[position]
        holder.apply {
            if (variant.variantImages.isNotEmpty()) {
                Glide.with(context).load(variant.variantImages[0].imageFullPath)
                    .into(ivVariantImage)
            }else{
                ivVariantImage.setImageResource(R.drawable.ic_no_image)
            }
            if (variant.variantPackSize) {
                tvVariantName.text = context.getString(R.string.variantForOneAdapter4)
                ivArrowDownRight.visibility = View.VISIBLE
            } else {
                tvVariantName.text = variant.variantName
                ivArrowDownRight.visibility = View.GONE
            }
            tvVariantSKU.text =
                StringBuffer(context.getString(R.string.variantForOneAdapter1) + variant.variantSKU)
            tvVariantRetailPrice.text =
                NumberFormat.getInstance(Locale.US).format(variant.variantRetailPrice).toString()
            tvVariantAvailable.text = StringBuffer(
                context.getString(R.string.variantForOneAdapter2) + NumberFormat.getInstance()
                    .format(variant.inventories[0].inventoryAvailable).toString()
            )
            tvVariantOnHand.text = StringBuffer(
                context.getString(R.string.variantForOneAdapter3) + NumberFormat.getInstance()
                    .format(variant.inventories[0].inventoryOnHand).toString()
            )
            itemView.setOnClickListener {
                onItemClickVariantForOne?.invoke(variant.productId, variant.variantId)
            }
        }
    }

    override fun getItemCount(): Int {
        return variantList.size
    }
}