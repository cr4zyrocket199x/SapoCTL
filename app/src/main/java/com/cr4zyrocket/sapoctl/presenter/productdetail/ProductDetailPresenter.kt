package com.cr4zyrocket.sapoctl.presenter.productdetail

import androidx.lifecycle.MutableLiveData
import com.cr4zyrocket.sapoctl.api.API
import com.cr4zyrocket.sapoctl.common.Common
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.model.Variant

class ProductDetailPresenter(
    private var productDetailInterfaceViewModel: ProductDetailInterface.ViewModel,
) :
    ProductDetailInterface.Presenter {
    companion object {
        private const val TAG = "ProductDetailPresenter"
    }

    var product = MutableLiveData<Product>()
    var variant = MutableLiveData<Variant>()
    var variantList = MutableLiveData<MutableList<Variant>>()
    var txtVariantTaxable = MutableLiveData<String>()
    var txtVariantSellable = MutableLiveData<String>()
    var isActive = MutableLiveData<Boolean>()
    var txtBtnDeleteTitle = MutableLiveData<String>()
    var txtToolbarTitle = MutableLiveData<String>()
    var txtShowProductTypeDetail = MutableLiveData<String>()
    var txtCompositeItemCount = MutableLiveData<String>()
    var txtInventoryOnHand = MutableLiveData<String>()
    var txtInventoryAvailable = MutableLiveData<String>()
    var txtInventoryPosition = MutableLiveData<String>()
    var txtProductType = MutableLiveData<String>()
    var txtProductStatus = MutableLiveData<String>()
    var txtProductWeight = MutableLiveData<String>()


    override suspend fun initData(productId: Long) {
        val product = getProduct(productId)
        productDetailInterfaceViewModel.showProductDetail(product)
        productDetailInterfaceViewModel.setMutableLiveData(product)
    }

    override fun showCompositeSubItemList() {
        productDetailInterfaceViewModel.moveToCompositeItemActivity()
    }

    override suspend fun getProduct(productId: Long): Product {
        var product = Product()
        val responseData = API.apiServiceGetData.getProduct(productId)
        if (responseData.isSuccessful) {
            responseData.body()?.product?.let {
                product = Common.mapProductToProductData(it)
            }
        }
        return product
    }
}