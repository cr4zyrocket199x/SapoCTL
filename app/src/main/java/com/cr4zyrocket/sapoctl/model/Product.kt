package com.cr4zyrocket.sapoctl.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class Product(
    var productID: Long = 0,

    var productName: String = "",

    var productStatus: String = "",

    var productBrandName: String = "",

    var productCategoryName: String = "",

    var productDescription: String = "",

    var productTags: String = "",

    var productType: String = "",

    var variants: @RawValue MutableList<Variant> = mutableListOf(),

    var productImages: @RawValue MutableList<Image> = mutableListOf(),

    var productOption1: String = "",

    var productOption2: String = "",

    var productOption3: String = ""
) : Parcelable