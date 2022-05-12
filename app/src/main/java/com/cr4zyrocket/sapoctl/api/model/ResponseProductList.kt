package com.cr4zyrocket.sapoctl.api.model

import com.google.gson.annotations.SerializedName

class ResponseProductList {
    @SerializedName("metadata")
    var metaData: MetaData? = null

    @SerializedName("products")
    var productList: MutableList<ProductData>? = null
}