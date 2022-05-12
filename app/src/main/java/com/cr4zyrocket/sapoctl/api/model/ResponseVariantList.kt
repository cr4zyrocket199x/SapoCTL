package com.cr4zyrocket.sapoctl.api.model


import com.google.gson.annotations.SerializedName

class ResponseVariantList {
    @SerializedName("metadata")
    var metaData: MetaData? = null

    @SerializedName("variants")
    var variantList: MutableList<VariantData>? = null
}