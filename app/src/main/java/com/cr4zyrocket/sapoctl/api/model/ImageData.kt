package com.cr4zyrocket.sapoctl.api.model

import com.google.gson.annotations.SerializedName

class ImageData {

    @SerializedName("full_path")
    var imageFullPath: String? = null

    @SerializedName("position")
    var imagePosition: Long? = -1
}