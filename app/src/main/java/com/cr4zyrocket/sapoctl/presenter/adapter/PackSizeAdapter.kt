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
import com.cr4zyrocket.sapoctl.model.Variant
import com.cr4zyrocket.sapoctl.presenter.variant_detail.VariantDetailActivity
import java.text.NumberFormat
import java.util.*

class PackSizeAdapter(private val context: Context, private val packSizes: MutableList<Variant>) :
    RecyclerView.Adapter<PackSizeAdapter.PackSizeViewHolder>() {
    private var packSizeList = mutableListOf<Variant>()

    init {
        packSizeList = packSizes
    }

    inner class PackSizeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArrowDownRight: ImageView = itemView.findViewById(R.id.ivArrowDownRight)
        val ivVariantImage: ImageView = itemView.findViewById(R.id.ivVariantImage)
        val tvVariantName: TextView = itemView.findViewById(R.id.tvVariantName)
        val tvVariantSKU: TextView = itemView.findViewById(R.id.tvVariantSKU)
        val tvVariantRetailPrice: TextView = itemView.findViewById(R.id.tvVariantRetailPrice)
        val tvVariantOnHand: TextView = itemView.findViewById(R.id.tvVariantOnHand)
        val tvVariantAvailable: TextView = itemView.findViewById(R.id.tvVariantAvailable)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PackSizeAdapter.PackSizeViewHolder {
        return PackSizeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_variant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PackSizeAdapter.PackSizeViewHolder, position: Int) {
        val packSize = packSizeList[position]
        holder.apply {
            if (packSize.variantImages.isEmpty()) {
                ivVariantImage.setImageResource(R.drawable.ic_no_image)
            } else {
                Glide.with(context).load(packSize.variantImages[0].imageFullPath).into(ivVariantImage)
            }
            ivArrowDownRight.setImageResource(R.drawable.ic_arrow_down_right)
            tvVariantName.text = context.getString(R.string.variantForOneAdapter4)
            tvVariantSKU.text =
                StringBuffer(context.getString(R.string.variantForOneAdapter1) + packSize.variantSKU)
            tvVariantRetailPrice.text =
                NumberFormat.getInstance(Locale.US).format(packSize.variantRetailPrice).toString()
            tvVariantAvailable.text = StringBuffer(
                context.getString(R.string.variantForOneAdapter2) + NumberFormat.getInstance()
                    .format(packSize.inventories[0].inventoryAvailable).toString()
            )
            tvVariantOnHand.text = StringBuffer(
                context.getString(R.string.variantForOneAdapter3) + NumberFormat.getInstance()
                    .format(packSize.inventories[0].inventoryOnHand).toString()
            )
            itemView.setOnClickListener {
                val intent = Intent(context, VariantDetailActivity::class.java)
                intent.putExtra(VariantDetailActivity.KEY_VARIANT, packSize)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return packSizeList.size
    }
}