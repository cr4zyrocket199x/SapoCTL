package com.cr4zyrocket.sapoctl.presenter.product

import androidx.lifecycle.MutableLiveData
import com.cr4zyrocket.sapoctl.api.API
import com.cr4zyrocket.sapoctl.common.Common
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.model.Variant

class ProductPresenter(
    private val productInterfaceViewModel: ProductInterface.ViewModel,
) :
    ProductInterface.Presenter {

    companion object {
        private const val TAG = "ProductPresenter"
    }

    var txtProductTitle = MutableLiveData<String>()
    var txtProductCount = MutableLiveData<String>()

    override suspend fun initData(isProductResult: Boolean, currentPage: Long) {
        if (isProductResult) {
            productInterfaceViewModel.showProductList(getProductList(currentPage, ""))
        } else {
            productInterfaceViewModel.showVariantList(getVariantList(currentPage, ""))
        }
        productInterfaceViewModel.setRefresh(false)
    }

    override suspend fun getProductList(
        currentPage: Long,
        keySearch: String
    ): MutableList<Product> {
        val productList = mutableListOf<Product>()
        val responseData = API.apiServiceGetData.getResponseProductList(currentPage, keySearch)
        if (responseData.isSuccessful) {
            responseData.body()?.productList?.forEach {
                productList.add(Common.mapProductToProductData(it))
            }
            productInterfaceViewModel.setMutableLiveData(Common.mapMetaToMetaData(responseData.body()!!.metaData!!))
        }
        return productList
    }


    override suspend fun getVariantList(
        currentPage: Long,
        keySearch: String
    ): MutableList<Variant> {
        val variantList = mutableListOf<Variant>()
        val responseData = API.apiServiceGetData.getResponseVariantList(currentPage, keySearch)
        if (responseData.isSuccessful) {
            responseData.body()?.let { it ->
                it.variantList?.forEach { variantData ->
                    variantList.add(Common.mapVariantToVariantData(variantData))
                }
                it.metaData?.let {
                    productInterfaceViewModel.setMutableLiveData(Common.mapMetaToMetaData(it))
                }
            }
        }
        return variantList
    }

}