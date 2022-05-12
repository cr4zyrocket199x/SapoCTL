package com.cr4zyrocket.sapoctl.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.model.Price
import java.text.NumberFormat
import java.util.*

class ProductPriceAdapter(private val context: Context, prices: MutableList<Price>) :
    RecyclerView.Adapter<ProductPriceAdapter.PriceViewHolder>() {
    private var priceList = mutableListOf<Price>()

    init {
        priceList = prices
    }

    inner class PriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPriceName: TextView = itemView.findViewById(R.id.tvPriceName)
        val tvPriceValue: TextView = itemView.findViewById(R.id.tvPriceValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        return PriceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_price, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val price = priceList[position]
        holder.tvPriceName.text = price.priceName
        holder.tvPriceValue.text =
            NumberFormat.getInstance(Locale.US).format(price.priceValue).toString()

    }

    override fun getItemCount(): Int {
        return priceList.size
    }
}