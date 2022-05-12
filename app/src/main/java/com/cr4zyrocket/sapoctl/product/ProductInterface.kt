package com.cr4zyrocket.sapoctl.product

import com.cr4zyrocket.sapoctl.api.model.ProductData
import com.cr4zyrocket.sapoctl.api.model.ResponseProductList
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.api.model.ResponseVariantList
import com.cr4zyrocket.sapoctl.model.Meta
import com.cr4zyrocket.sapoctl.model.Variant


interface ProductInterface {
    interface ViewModel {
        fun setRefresh(isRefresh: Boolean)

        fun showProductList(productList: MutableList<Product>)

        fun showVariantList(variantList: MutableList<Variant>)

        fun setMutableLiveData(meta: Meta)
    }

    interface Presenter {
        suspend fun initData(isProductResult: Boolean, currentPage: Long)

        suspend fun getProductList(currentPage: Long): MutableList<Product>

        suspend fun getVariantList(currentPage: Long): MutableList<Variant>

    }
}