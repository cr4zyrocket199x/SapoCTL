package com.cr4zyrocket.sapoctl.presenter.variant_detail

import androidx.databinding.Bindable
import androidx.databinding.BindingMethods
import androidx.databinding.Observable
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.model.Variant

interface VariantDetailInterface {
    interface ViewModel {
        fun showVariantDetail(product: Product, variant: Variant)

        fun setMutableLiveData(product: Product, variant: Variant)

        fun moveToCompositeItemActivity()
    }

    interface Presenter {
        suspend fun initData(productId: Long, variantId: Long)

        suspend fun getProduct(productId: Long): Product

        suspend fun getVariant(productId: Long, variantId: Long): Variant

        fun showCompositeSubItemList()
    }
}