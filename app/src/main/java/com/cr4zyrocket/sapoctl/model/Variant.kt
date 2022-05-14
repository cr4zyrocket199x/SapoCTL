package com.cr4zyrocket.sapoctl.model

class Variant {

    var variantId: Long = 0

    lateinit var variantName: String

    lateinit var variantSKU: String

    lateinit var variantBarCode: String

    lateinit var productUnit: String

    var variantRetailPrice: Long = 0

    var variantSellable: Boolean = false

    var variantTaxable: Boolean = false

    lateinit var variantWeightUnit: String

    lateinit var productType: String

    lateinit var productOption1: String

    lateinit var productOption2: String

    lateinit var productOption3: String

    var productId: Long = 0

    var variantWeightValue: Double = 0.0

    lateinit var inventories: MutableList<Inventory>

    var variantPackSizeQuantity: Long = 0

    var variantPackSizeRootId: Long = 0

    var variantPackSize: Boolean = false

    lateinit var variantImages: MutableList<Image>

    lateinit var variantPrices: MutableList<Price>

    lateinit var variantCompositeItems: MutableList<CompositeItem>

}