package com.cr4zyrocket.sapoctl.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Inventory(
    var inventoryOnHand: Double = 0.0,

    var inventoryAvailable: Double = 0.0,

    var inventoryPosition: String = ""
)