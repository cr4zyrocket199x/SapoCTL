package com.cr4zyrocket.sapoctl.api.model

import com.google.gson.annotations.SerializedName

class ResponseProduct {
    @SerializedName("product")
    var product: ProductData? = null
}