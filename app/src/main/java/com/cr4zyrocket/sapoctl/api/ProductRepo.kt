package com.cr4zyrocket.sapoctl.api

import com.cr4zyrocket.sapoctl.api.model.ProductData

interface ProductRepo {
    fun getListProduct(): List<ProductData>
}