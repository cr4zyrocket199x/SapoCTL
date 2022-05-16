package com.cr4zyrocket.sapoctl.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class Variant(

    var variantId: Long = 0,

    var variantName: String = "",

    var variantSKU: String = "",

    var variantBarCode: String = "",

    var variantUnit: String = "",

    var variantRetailPrice: Long = 0,

    var variantSellable: Boolean = false,

    var variantTaxable: Boolean = false,

    var variantWeightUnit: String = "",

    var productType: String = "",

    var productOption1: String = "",

    var productOption2: String = "",

    var productOption3: String = "",

    var productId: Long = 0,

    var variantWeightValue: Double = 0.0,

    var inventories: @RawValue MutableList<Inventory> = mutableListOf(),

    var variantPackSizeQuantity: Long = 0,

    var variantPackSizeRootId: Long = 0,

    var variantPackSize: Boolean = false,

    var variantImages: @RawValue MutableList<Image> = mutableListOf(),

    var variantPrices: @RawValue MutableList<Price> = mutableListOf(),

    var variantCompositeItems: @RawValue MutableList<CompositeItem> = mutableListOf(),

    ) : Parcelable