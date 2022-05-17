package com.cr4zyrocket.sapoctl.presenter.productdetail

import com.cr4zyrocket.sapoctl.model.Product

interface ProductDetailInterface {
    interface ViewModel {
        fun showProductDetail(product: Product)

        fun setMutableLiveData(product: Product)

        fun moveToCompositeItemActivity()
    }

    interface Presenter {

        suspend fun initData(productId: Long)

        suspend fun getProduct(productId: Long): Product

        fun showCompositeSubItemList()
    }
}