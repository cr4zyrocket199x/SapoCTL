package com.cr4zyrocket.sapoctl.api.model

import com.google.gson.annotations.SerializedName

class CompositeItemData {
    @SerializedName("sub_name")
    var compositeItemSubName: String? = null

    @SerializedName("sub_sku")
    var compositeItemSubSKU: String? = null
}