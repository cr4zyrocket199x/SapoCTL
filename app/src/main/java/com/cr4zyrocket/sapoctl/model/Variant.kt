package com.cr4zyrocket.sapoctl.model

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

    var inventories: MutableList<Inventory> = mutableListOf(),

    var variantPackSizeQuantity: Long = 0,

    var variantPackSizeRootId: Long = 0,

    var variantPackSize: Boolean = false,

    var variantImages: MutableList<Image> = mutableListOf(),

    var variantPrices: MutableList<Price> = mutableListOf(),

    var variantCompositeItems: MutableList<CompositeItem> = mutableListOf(),

    )