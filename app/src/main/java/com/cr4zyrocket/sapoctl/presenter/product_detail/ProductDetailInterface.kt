package com.cr4zyrocket.sapoctl.presenter.product_detail

import com.cr4zyrocket.sapoctl.model.Product

interface ProductDetailInterface {
    interface ViewModel {
        fun showProductDetail(product: Product)

        fun setMutableLiveData(product: Product)

        fun moveToCompositeItemActivity()
    }

    interface Presenter {

        suspend fun initData(product: Product)


        fun showCompositeSubItemList()
    }
}