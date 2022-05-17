package com.cr4zyrocket.sapoctl.presenter.compositeitem

import com.cr4zyrocket.sapoctl.model.Variant

interface CompositeItemInterface {
    interface ViewModel {

        fun showCompositeSubItemList(variant: Variant,compositeSubItemList: MutableList<Variant>)

        fun setMutableLiveData(variant: Variant)
    }

    interface Presenter {
        suspend fun initData(productId: Long, variantId: Long)

        suspend fun getVariant(productId: Long, variantId: Long): Variant
    }
}