package com.cr4zyrocket.sapoctl.model

data class CompositeItem(
    var compositeSubItemId: Long = 0,

    var compositeSubItemProductId: Long = 0,

    var compositeSubItemName: String = "",

    var compositeSubItemSKU: String = "",

    var compositeSubItemPrice: Long = 0,

    var compositeSubItemQuantity: Long = 0,

    var compositeSubItemType: String = "",
)