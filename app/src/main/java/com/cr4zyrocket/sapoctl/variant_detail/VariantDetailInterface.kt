package com.cr4zyrocket.sapoctl.variant_detail

import com.cr4zyrocket.sapoctl.api.model.ProductData
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.model.Variant

interface VariantDetailInterface {
    interface ViewModel {
        fun showVariantDetail(product: Product, variant: Variant)

        fun setMutableLiveData(product: Product, variant: Variant)
    }

    interface Presenter {
        suspend fun initData(productId: Long, variantId: Long)

        suspend fun getProduct(productId: Long): Product

        suspend fun getVariant(productId: Long, variantId: Long): Variant
    }
}