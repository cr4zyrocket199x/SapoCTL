package com.cr4zyrocket.sapoctl.api.model

import com.google.gson.annotations.SerializedName

class CompositeItemData {
    @SerializedName("sub_variant_id")
    var compositeSubItemId: Long? = -1

    @SerializedName("sub_product_id")
    var compositeSubItemProductId: Long? = -1

    @SerializedName("sub_name")
    var compositeSubItemName: String? = null

    @SerializedName("sub_sku")
    var compositeSubItemSKU: String? = null

    @SerializedName("price")
    var compositeSubItemPrice: Long? = -1

    @SerializedName("quantity")
    var compositeSubItemQuantity: Long? = -1

    @SerializedName("sub_product_type")
    var compositeSubItemType: String? = null
}