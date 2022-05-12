package com.cr4zyrocket.sapoctl.presenter.adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.presenter.variant_detail.VariantDetailActivity
import com.cr4zyrocket.sapoctl.model.Variant
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class VariantAdapter(private val context: Context, private val variants: MutableList<Variant>) :
    RecyclerView.Adapter<VariantAdapter.VariantViewHolder>(), Filterable {
    private var variantList = mutableListOf<Variant>()

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val inputText = p0.toString().lowercase()
                variantList = if (inputText.isEmpty()) {
                    variants
                } else {
                    variants.filter { variant ->
                        variant.variantName.lowercase().contains(inputText) ||
                                variant.variantSKU.lowercase().contains(inputText) ||
                                variant.variantBarCode.lowercase().contains(inputText)
                    } as MutableList<Variant>
                }
                val filterResult = FilterResults()
                filterResult.values = variantList
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                variantList = p1?.values as MutableList<Variant>
                notifyDataSetChanged()
            }

        }
    }

    override fun onBindViewHolder(holder: VariantAdapter.VariantViewHolder, position: Int) {
        val variant = variantList[position]
        if (variant.variantImages.isNotEmpty()) {
            Glide.with(context).load(variant.variantImages[0].imageFullPath).into(holder.ivSingleVariantImage)
        } else {
            holder.ivSingleVariantImage.setImageResource(R.drawable.ic_no_image)
        }
        holder.tvSingleVariantName.text = variant.variantName
        holder.tvSingleVariantSKU.text =
            StringBuffer(context.getString(R.string.variantAdapter1) + variant.variantSKU)
        holder.tvSingleVariantRetailPrice.text = NumberFormat.getInstance(
            Locale.US
        ).format(variant.variantRetailPrice).toString()
        holder.tvSingleVariantAvailable.text =
            NumberFormat.getInstance().format(variant.inventories[0].inventoryAvailable).toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(context, VariantDetailActivity::class.java)
            intent.putExtra("productId", variant.productId)
            intent.putExtra("variantId", variant.variantId)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VariantAdapter.VariantViewHolder {
        return VariantViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_single_variant, parent, false)
        )
    }

}