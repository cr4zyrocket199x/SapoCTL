package com.cr4zyrocket.sapoctl.api.model

import com.google.gson.annotations.SerializedName

class PriceData {
    @SerializedName("name")
    var priceName: String? = null

    @SerializedName("value")
    var priceValue: Long? = -1
}