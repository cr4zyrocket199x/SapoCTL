package com.cr4zyrocket.sapoctl.presenter.product.adapter

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

class VariantAdapter(private val context: Context, private val variants: MutableList<Variant>) :
    RecyclerView.Adapter<VariantAdapter.VariantViewHolder>() {
    private var variantList = mutableListOf<Variant>()
    var onItemClickVariant: ((Long, Long) -> Unit)? = null

    init {
        variantList = variants
    }

    inner class VariantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSingleVariantImage: ImageView = itemView.findViewById(R.id.ivSingleVariantImage)
        val tvSingleVariantName: TextView = itemView.findViewById(R.id.tvSingleVariantName)
        val tvSingleVariantSKU: TextView = itemView.findViewById(R.id.tvSingleVariantSKU)
        val tvSingleVariantRetailPrice: TextView =
            itemView.findViewById(R.id.tvSingleVariantRetailPrice)
        val tvSingleVariantAvailable: TextView =
            itemView.findViewById(R.id.tvSingleVariantAvailable)
    }


    override fun getItemCount(): Int {
        return variantList.size
    }

    override fun onBindViewHolder(holder: VariantViewHolder, position: Int) {
        val variant = variantList[position]
        holder.apply {
            if (variant.variantImages.isNotEmpty()){
                Glide.with(context).load(variant.variantImages[0].imageFullPath).into(ivSingleVariantImage)
            }else{
                ivSingleVariantImage.setImageResource(R.drawable.ic_no_image)
            }
            tvSingleVariantName.text = variant.variantName
            tvSingleVariantSKU.text =
                StringBuffer(context.getString(R.string.variantAdapter1) + variant.variantSKU)
            tvSingleVariantRetailPrice.text = NumberFormat.getInstance(
                Locale.US
            ).format(variant.variantRetailPrice).toString()
            tvSingleVariantAvailable.text =
                NumberFormat.getInstance().format(variant.inventories[0].inventoryAvailable).toString()
            itemView.setOnClickListener {
                onItemClickVariant?.invoke(variant.productId, variant.variantId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VariantViewHolder {
        return VariantViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_single_variant, parent, false)
        )
    }

}