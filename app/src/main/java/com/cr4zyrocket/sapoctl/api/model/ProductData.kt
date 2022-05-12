package com.cr4zyrocket.sapoctl.api.model

import com.cr4zyrocket.sapoctl.model.Image
import com.cr4zyrocket.sapoctl.model.Variant
import com.google.gson.annotations.SerializedName

class ProductData {
    @SerializedName("id")
    var productID: Long? = -1

    @SerializedName("name")
    var productName: String? = null

    @SerializedName("status")
    var productStatus: String? = null

    @SerializedName("brand")
    var productBrandName: String? = null

    @SerializedName("category")
    var productCategoryName: String? = null

    @SerializedName("description")
    var productDescription: String? = null

    @SerializedName("tags")
    var productTags: String? = null

    @SerializedName("product_type")
    var productType: String? = null

    @SerializedName("variants")
    var variants: MutableList<VariantData>? = null

    @SerializedName("images")
    var productImages: MutableList<ImageData>? = null

    @SerializedName("opt1")
    var productOption1: String? = null

    @SerializedName("opt2")
    var productOption2: String? = null

    @SerializedName("opt3")
    var productOption3: String? = null
}