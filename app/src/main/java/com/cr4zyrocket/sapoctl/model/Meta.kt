package com.cr4zyrocket.sapoctl.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(
    var metaTotal: Long = 0,

    var metaPage: Long = 0,

    var metaLimit: Long = 0
) : Parcelable