package com.cr4zyrocket.sapoctl.model


data class Product(
    var productID: Long = 0,

    var productName: String = "",

    var productStatus: String = "",

    var productBrandName: String = "",

    var productCategoryName: String = "",

    var productDescription: String = "",

    var productTags: String = "",

    var productType: String = "",

    var variants: MutableList<Variant> = mutableListOf(),

    var productImages: MutableList<Image> = mutableListOf(),

    var productOption1: String = "",

    var productOption2: String = "",

    var productOption3: String = ""
)