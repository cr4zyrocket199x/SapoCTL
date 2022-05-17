package com.cr4zyrocket.sapoctl.model

data class Inventory(
    var inventoryOnHand: Double = 0.0,

    var inventoryAvailable: Double = 0.0,

    var inventoryPosition: String = ""
)