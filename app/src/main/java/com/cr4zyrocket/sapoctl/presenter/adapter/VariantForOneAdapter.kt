package com.cr4zyrocket.sapoctl.presenter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.presenter.variant_detail.VariantDetailActivity
import com.cr4zyrocket.sapoctl.model.Variant
import java.text.NumberFormat
import java.util.*

class VariantForOneAdapter(
    private val context: Context,
    variants: MutableList<Variant>
) :
    RecyclerView.Adapter<VariantForOneAdapter.VariantViewHolder>() {
    private var variantList = mutableListOf<Variant>()

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
        if (variant.variantImages.isEmpty()) {
            holder.ivVariantImage.setImageResource(R.drawable.ic_no_image)
        } else {
            Glide.with(context).load(variant.variantImages[0].imageFullPath).into(holder.ivVariantImage)
        }

        if (variant.variantPackSize) {
            holder.tvVariantName.text = context.getString(R.string.variantForOneAdapter4)
            holder.ivArrowDownRight.visibility = View.VISIBLE
        } else {
            holder.tvVariantName.text = variant.variantName
            holder.ivArrowDownRight.visibility = View.GONE
        }
        holder.tvVariantSKU.text =
            StringBuffer(context.getString(R.string.variantForOneAdapter1) + variant.variantSKU)
        holder.tvVariantRetailPrice.text =
            NumberFormat.getInstance(Locale.US).format(variant.variantRetailPrice).toString()
        holder.tvVariantAvailable.text = StringBuffer(
            context.getString(R.string.variantForOneAdapter2) + NumberFormat.getInstance()
                .format(variant.inventories[0].inventoryAvailable).toString()
        )
        holder.tvVariantOnHand.text = StringBuffer(
            context.getString(R.string.variantForOneAdapter3) + NumberFormat.getInstance()
                .format(variant.inventories[0].inventoryOnHand).toString()
        )
        holder.itemView.setOnClickListener {
            val intent = Intent(context, VariantDetailActivity::class.java)
            intent.putExtra(VariantDetailActivity.KEY_PRODUCT_ID, variant.productId)
            intent.putExtra(VariantDetailActivity.KEY_VARIANT_ID, variant.variantId)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return variantList.size
    }
}