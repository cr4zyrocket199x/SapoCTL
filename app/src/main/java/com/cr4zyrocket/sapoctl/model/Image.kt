package com.cr4zyrocket.sapoctl.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(

    var imageFullPath: String = "",

    var imagePosition: Long = 0
) : Parcelable