package com.cr4zyrocket.sapoctl.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Price(

    var priceName: String = "",


    var priceValue: Long = 0
) : Parcelable