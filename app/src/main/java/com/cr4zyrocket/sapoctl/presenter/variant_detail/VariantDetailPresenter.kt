package com.cr4zyrocket.sapoctl.presenter.variant_detail

import androidx.lifecycle.MutableLiveData
import com.cr4zyrocket.sapoctl.api.API
import com.cr4zyrocket.sapoctl.common.Common
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.model.Variant

class VariantDetailPresenter(
    private val variantDetailInterfaceViewModel: VariantDetailInterface.ViewModel,
) : VariantDetailInterface.Presenter {
    companion object {
        private const val TAG = "ProductDetailPresenter"
    }

    private var common = Common()
    var product = MutableLiveData<Product>()
    var variant = MutableLiveData<Variant>()
    var txtVariantTaxable = MutableLiveData<String>()
    var txtVariantSellable = MutableLiveData<String>()
    var txtBtnDeleteTitle = MutableLiveData<String>()
    var txtToolbarTitle = MutableLiveData<String>()
    var txtShowProductTypeDetail = MutableLiveData<String>()
    var txtCompositeItemCount = MutableLiveData<String>()
    var txtInventoryOnHand = MutableLiveData<String>()
    var txtInventoryAvailable = MutableLiveData<String>()
    var txtInventoryPosition = MutableLiveData<String>()
    var txtProductType = MutableLiveData<String>()
    var txtVariantWeight = MutableLiveData<String>()

    override suspend fun initData(productId: Long, variant: Variant) {
        val product = getProduct(productId)
        variantDetailInterfaceViewModel.showVariantDetail(product, variant)
        variantDetailInterfaceViewModel.setMutableLiveData(product, variant)
    }

    override suspend fun getProduct(productId: Long): Product {
        var product = Product()
        val responseData = API.apiServiceGetData.getProduct(productId)
        if (responseData.isSuccessful) {
            responseData.body()?.product?.let {
                product = common.mapProductToProductData(it)
            }
        }
        return product
    }

    override fun showCompositeSubItemList() {
        variantDetailInterfaceViewModel.moveToCompositeItemActivity()
    }
}