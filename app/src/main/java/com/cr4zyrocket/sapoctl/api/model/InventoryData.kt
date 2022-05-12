package com.cr4zyrocket.sapoctl.api.model

import com.google.gson.annotations.SerializedName

class InventoryData {
    @SerializedName("on_hand")
    var inventoryOnHand: Double? = -0.0

    @SerializedName("available")
    var inventoryAvailable: Double? = -0.0

    @SerializedName("bin_location")
    var inventoryPosition: String? = null
}