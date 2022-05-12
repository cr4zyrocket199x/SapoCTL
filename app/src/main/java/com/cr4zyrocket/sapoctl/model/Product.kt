package com.cr4zyrocket.sapoctl.model


class Product {
    var productID: Long = 0

    lateinit var productName: String

    lateinit var productStatus: String

    lateinit var productBrandName: String

    lateinit var productCategoryName: String

    lateinit var productDescription: String

    lateinit var productTags: String

    lateinit var productType: String

    lateinit var variants: MutableList<Variant>

    lateinit var productImages: MutableList<Image>

    lateinit var productOption1: String

    lateinit var productOption2: String

    lateinit var productOption3: String
}