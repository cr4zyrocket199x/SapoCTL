package com.cr4zyrocket.sapoctl.presenter.composite_item

import androidx.lifecycle.MutableLiveData
import com.cr4zyrocket.sapoctl.api.API
import com.cr4zyrocket.sapoctl.common.Common
import com.cr4zyrocket.sapoctl.model.Variant

class CompositeItemPresenter (private val compositeItemInterfaceViewModel: CompositeItemInterface.ViewModel):CompositeItemInterface.Presenter{
    companion object {
        private const val TAG = "CompositeItemPresenter"
    }

    var txtCompositeSubItemsPrice = MutableLiveData<String>()
    var txtCompositeSubItemsCount = MutableLiveData<String>()
    private var common = Common()

    override suspend fun initData(productId: Long,variantId: Long) {
        val responseData = API.apiServiceGetData.getVariant(productId,variantId)
        if (responseData.isSuccessful){
            responseData.body()?.variant?.let {
                val variant=common.mapVariantToVariantData(it)
                val compositeSubItemList= mutableListOf<Variant>()
                variant.variantCompositeItems.forEach{compositeSubItem ->
                    compositeSubItemList.add(getVariant(compositeSubItem.compositeSubItemProductId,compositeSubItem.compositeSubItemId))
                }
                compositeItemInterfaceViewModel.showCompositeSubItemList(variant,compositeSubItemList)
                compositeItemInterfaceViewModel.setMutableLiveData(variant)

            }
        }
    }

    override suspend fun getVariant(productId: Long, variantId: Long): Variant {
        var variant = Variant()
        val responseData = API.apiServiceGetData.getVariant(productId, variantId)
        if (responseData.isSuccessful) {
            responseData.body()?.variant?.let {
                variant = common.mapVariantToVariantData(it)
            }
        }
        return variant
    }

}