package com.cr4zyrocket.sapoctl.product_detail

import com.cr4zyrocket.sapoctl.api.model.ProductData
import com.cr4zyrocket.sapoctl.model.Product

interface ProductDetailInterface {
    interface ViewModel {
        fun showProductDetail(product: Product)

        fun setMutableLiveData(product: Product)
    }

    interface Presenter {

        suspend fun initData(productId: Long)

        suspend fun getProduct(productId: Long): Product
    }
}