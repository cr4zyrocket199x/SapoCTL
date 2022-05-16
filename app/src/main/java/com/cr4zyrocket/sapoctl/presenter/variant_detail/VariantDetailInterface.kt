package com.cr4zyrocket.sapoctl.presenter.variant_detail

import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.model.Variant

interface VariantDetailInterface {
    interface ViewModel {
        fun showVariantDetail(product: Product, variant: Variant)

        fun setMutableLiveData(product: Product, variant: Variant)

        fun moveToCompositeItemActivity()
    }

    interface Presenter {
        suspend fun initData(productId: Long, variant: Variant)

        suspend fun getProduct(productId: Long): Product

        fun showCompositeSubItemList()
    }
}