package com.cr4zyrocket.sapoctl.model

class CompositeItem {
    var compositeSubItemId: Long = 0

    var compositeSubItemProductId: Long = 0

    lateinit var compositeSubItemName: String

    lateinit var compositeSubItemSKU: String

    var compositeSubItemPrice: Long = 0

    var compositeSubItemQuantity: Long = 0

    lateinit var compositeSubItemType: String
}