package com.cr4zyrocket.sapoctl.presenter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.presenter.product_detail.ProductDetailActivity
import com.cr4zyrocket.sapoctl.model.Product
import com.squareup.picasso.Picasso
import java.text.NumberFormat

class ProductAdapter(
    private val context: Context,
    private val product: MutableList<Product>
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    private var productList = mutableListOf<Product>()

    init {
        productList = product
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSingleProductImage: ImageView = itemView.findViewById(R.id.ivSingleProductImage)
        val tvSingleProductName: TextView = itemView.findViewById(R.id.tvSingleProductName)
        val tvSingleProductVariantCount: TextView =
            itemView.findViewById(R.id.tvSingleProductVariantCount)
        val tvSingleProductAvailable: TextView =
            itemView.findViewById(R.id.tvSingleProductAvailable)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_single_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val product = productList[position]
        var allAvailable = 0.0
        for (i in 0 until product.variants.size) {
            if (!product.variants[i].variantPackSize) {
                allAvailable += product.variants[i].inventories[0].inventoryAvailable
            }
        }
        holder.tvSingleProductName.text = product.productName
        holder.tvSingleProductVariantCount.text = product.variants.size.toString()
        holder.tvSingleProductAvailable.text = NumberFormat.getInstance().format(allAvailable)
        if (product.productImages.isNotEmpty()) {
            for (i in 0 until product.productImages.size) {
                if (product.productImages[i].imagePosition == 1L) {
                    Picasso.get().load(product.productImages[i].imageFullPath)
                        .fit().centerCrop()
                        .into(holder.ivSingleProductImage)
                }
            }
        } else {
            holder.ivSingleProductImage.setImageResource(R.drawable.ic_no_image)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("productId", product.productID)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}