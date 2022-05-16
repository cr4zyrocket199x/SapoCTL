package com.cr4zyrocket.sapoctl.common

import com.cr4zyrocket.sapoctl.api.model.*
import com.cr4zyrocket.sapoctl.model.*

class Common {
    fun mapProductToProductData(productData: ProductData): Product {
        return Product().apply {
            val variantList = mutableListOf<Variant>()
            productID = productData.productID ?: 0
            productName = productData.productName ?: "_ _ _"
            productStatus = productData.productStatus ?: "_ _ _"
            productBrandName = productData.productBrandName ?: "_ _ _"
            productCategoryName = productData.productCategoryName ?: "_ _ _"
            productDescription = productData.productDescription ?: "_ _ _"
            productTags = when (productData.productTags) {
                null -> {
                    "_ _ _"
                }
                "" -> {
                    "_ _ _"
                }
                else -> {
                    productData.productTags.toString()
                }
            }
            productType = productData.productType ?: "_ _ _"
            productOption1 = productData.productOption1 ?: "_ _ _"
            productOption2 = productData.productOption2 ?: "_ _ _"
            productOption3 = productData.productOption3 ?: "_ _ _"
            productData.variants?.forEach {
                variantList.add(mapVariantToVariantData(it))
            } ?: mutableListOf<Variant>()
            variants = variantList
            productImages = mapImageListToImageDataList(productData.productImages)
        }
    }

    fun mapVariantToVariantData(variantData: VariantData): Variant {
        return Variant().apply {
            val variantCompositeItemList = mutableListOf<CompositeItem>()
            variantId = variantData.variantId ?: 0
            variantName = variantData.variantName ?: "_ _ _"
            variantSKU = variantData.variantSKU ?: "_ _ _"
            variantBarCode = variantData.variantBarCode ?: "_ _ _"
            variantUnit = variantData.variantUnit ?: "_ _ _"
            variantRetailPrice = variantData.variantRetailPrice ?: 0
            variantSellable = variantData.variantSellable ?: false
            variantTaxable = variantData.variantTaxable ?: false
            variantWeightUnit = variantData.variantWeightUnit ?: "_ _ _"
            productType = variantData.productType ?: "_ _ _"
            productOption1 = variantData.productOption1 ?: "_ _ _"
            productOption2 = variantData.productOption2 ?: "_ _ _"
            productOption3 = variantData.productOption3 ?: "_ _ _"
            productId = variantData.productId ?: 0
            variantWeightValue = variantData.variantWeightValue ?: 0.0
            inventories = mapInventoryListToInventoryDataList(variantData.inventories)
            variantPackSizeQuantity = variantData.variantPackSizeQuantity ?: 0
            variantPackSizeRootId = variantData.variantPackSizeRootId ?: 0
            variantPackSize = variantData.variantPackSize ?: false
            variantImages = mapImageListToImageDataList(variantData.variantImages)
            variantPrices = mapPriceListToPriceDataList(variantData.variantPrices)
            variantData.variantCompositeItems?.forEach {
                variantCompositeItemList.add(mapCompositeItemToCompositeItemData(it))
            } ?: mutableListOf<Variant>()
            variantCompositeItems = variantCompositeItemList
        }
    }

    private fun mapInventoryListToInventoryDataList(inventoryDataList: MutableList<InventoryData>?): MutableList<Inventory> {
        val inventoryList = mutableListOf<Inventory>()
        inventoryDataList?.forEach {
            inventoryList.add(Inventory().apply {
                inventoryOnHand = it.inventoryOnHand ?: 0.0
                inventoryAvailable = it.inventoryAvailable ?: 0.0
                inventoryPosition = it.inventoryPosition ?: "_ _ _"
            })
        }
        return inventoryList
    }

    private fun mapImageListToImageDataList(imageDataList: MutableList<ImageData>?): MutableList<Image> {
        val imageList = mutableListOf<Image>()
        imageDataList?.forEach {
            imageList.add(Image().apply {
                imageFullPath = it.imageFullPath ?: "_ _ _"
                imagePosition = it.imagePosition ?: 0
            })
        }
        return imageList
    }

    fun mapMetaToMetaData(metaData: MetaData): Meta {
        return Meta().apply {
            metaTotal = metaData.metaDataTotal ?: 0
            metaLimit = metaData.metaDataLimit ?: 0
            metaPage = metaData.metaDataPage ?: 0
        }
    }

    private fun mapPriceListToPriceDataList(priceDataList: MutableList<PriceData>?): MutableList<Price> {
        val priceList = mutableListOf<Price>()
        priceDataList?.forEach {
            priceList.add(Price().apply {
                priceName = it.priceName ?: "_ _ _"
                priceValue = it.priceValue ?: 0
            })
        }
        return priceList
    }

    private fun mapCompositeItemToCompositeItemData(compositeItemData: CompositeItemData): CompositeItem {
        return CompositeItem().apply {
            compositeSubItemId = compositeItemData.compositeSubItemId ?: 0
            compositeSubItemProductId = compositeItemData.compositeSubItemProductId ?: 0
            compositeSubItemName = compositeItemData.compositeSubItemName ?: "_ _ _"
            compositeSubItemSKU = compositeItemData.compositeSubItemSKU ?: "_ _ _"
            compositeSubItemPrice = compositeItemData.compositeSubItemPrice ?: 0
            compositeSubItemQuantity = compositeItemData.compositeSubItemQuantity ?: 0
            compositeSubItemType = compositeItemData.compositeSubItemType ?: "_ _ _"
        }
    }
}