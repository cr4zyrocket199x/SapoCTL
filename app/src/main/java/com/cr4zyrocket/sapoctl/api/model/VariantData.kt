package com.cr4zyrocket.sapoctl.api.model

import com.google.gson.annotations.SerializedName

class VariantData {
    @SerializedName("id")
    var variantId: Long? = -1

    @SerializedName("name")
    var variantName: String? = null

    @SerializedName("sku")
    var variantSKU: String? = null

    @SerializedName("barcode")
    var variantBarCode: String? = null

    @SerializedName("variant_retail_price")
    var variantRetailPrice: Long? = -1

    @SerializedName("sellable")
    var variantSellable: Boolean? = null

    @SerializedName("taxable")
    var variantTaxable: Boolean? = null

    @SerializedName("weight_unit")
    var variantWeightUnit: String? = null

    @SerializedName("unit")
    var variantUnit: String? = null

    @SerializedName("product_type")
    var productType: String? = null

    @SerializedName("opt1")
    var productOption1: String? = null

    @SerializedName("opt2")
    var productOption2: String? = null

    @SerializedName("opt3")
    var productOption3: String? = null

    @SerializedName("product_id")
    var productId: Long? = -1

    @SerializedName("weight_value")
    var variantWeightValue: Double? = -0.0

    @SerializedName("inventories")
    var inventories: MutableList<InventoryData>? = null

    @SerializedName("packsize_quantity")
    var variantPackSizeQuantity: Long? = -1

    @SerializedName("packsize_root_id")
    var variantPackSizeRootId: Long? = -1

    @SerializedName("packsize")
    var variantPackSize: Boolean? = null

    @SerializedName("images")
    var variantImages: MutableList<ImageData>? = null

    @SerializedName("variant_prices")
    var variantPrices: MutableList<PriceData>? = null

    @SerializedName("composite_items")
    var variantCompositeItems: MutableList<CompositeItemData>? = null

}