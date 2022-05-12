package com.cr4zyrocket.sapoctl.model

import com.google.gson.annotations.SerializedName

class Inventory {
    var inventoryOnHand: Double = 0.0

    var inventoryAvailable: Double = 0.0

    lateinit var inventoryPosition: String
}