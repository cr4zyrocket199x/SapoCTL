package com.cr4zyrocket.sapoctl.api.model

import com.google.gson.annotations.SerializedName

class MetaData {
    @SerializedName("total")
    var metaDataTotal: Long? = -1

    @SerializedName("page")
    var metaDataPage: Long? = -1

    @SerializedName("limit")
    var metaDataLimit: Long? = -1
}