package com.cr4zyrocket.sapoctl.presenter.variant_detail

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.databinding.ActivityVariantDetailBinding
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.model.Variant
import com.cr4zyrocket.sapoctl.presenter.adapter.PackSizeAdapter
import com.cr4zyrocket.sapoctl.presenter.adapter.ProductImageAdapter
import com.cr4zyrocket.sapoctl.presenter.adapter.ProductPriceAdapter
import com.cr4zyrocket.sapoctl.presenter.composite_item.CompositeItemActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class VariantDetailActivity : AppCompatActivity(), VariantDetailInterface.ViewModel {
    companion object {
        private const val TAG = "VariantDetailActivity"
        const val KEY_VARIANT = "variant"
    }

    private lateinit var binding: ActivityVariantDetailBinding
    private lateinit var variant: Variant
    private var variantDetailPresenter = VariantDetailPresenter(this)
    private var packSizeList = mutableListOf<Variant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_variant_detail)
        setSupportActionBar(binding.tbVariantDetailToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        getIntentExtra()

        initData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showVariantDetail(product: Product, variant: Variant) {
        Handler(Looper.getMainLooper()).post {
            binding.ivVariantDetailVariantImage.visibility = View.VISIBLE
            binding.crdVariantDetailVariantPackSize.visibility = View.VISIBLE

            binding.trVariantDetailPackSizeCount.visibility = View.GONE
            binding.llVariantDetailCompositeDetail.visibility = View.GONE
            binding.trVariantDetailCompositeCategoryName.visibility = View.GONE
            binding.trVariantDetailCompositeBrandName.visibility = View.GONE
            binding.trVariantDetailCompositeTag.visibility = View.GONE
            binding.trVariantDetailVariantType.visibility = View.GONE
            binding.llVariantDetailImageList.visibility = View.GONE
            binding.tvVariantDetailShowVariantTypeDetail.visibility = View.GONE

            if(variant.variantWeightUnit==""||variant.variantWeightUnit=="g") {
                variantDetailPresenter.txtVariantWeight.value =
                    NumberFormat.getInstance(Locale.US).format(variant.variantWeightValue).toString() + "g"
            }else{
                variantDetailPresenter.txtVariantWeight.value =
                    NumberFormat.getInstance(Locale.US).format(variant.variantWeightValue*1000).toString() + "g"
            }
            if (variant.variantImages.isNotEmpty()) {
                Glide.with(this).load(variant.variantImages[0].imageFullPath).into(binding.ivVariantDetailVariantImage)
            } else {
                binding.llVariantDetailImageList.visibility = View.VISIBLE

                binding.ivVariantDetailVariantImage.visibility = View.GONE
            }
            binding.rclvVariantDetailVariantPriceList.apply {
                layoutManager = GridLayoutManager(applicationContext, 2)
                adapter = ProductPriceAdapter(applicationContext, variant.variantPrices)
            }
            when (variant.productType) {
                "lots_date" -> {
                    binding.trVariantDetailVariantType.visibility = View.VISIBLE
                    binding.tvVariantDetailShowVariantTypeDetail.visibility = View.VISIBLE
                }
                "serial" -> {
                    binding.trVariantDetailVariantType.visibility = View.VISIBLE
                    binding.tvVariantDetailShowVariantTypeDetail.visibility = View.VISIBLE

                    binding.crdVariantDetailVariantPackSize.visibility = View.GONE
                }
                "composite" -> {
                    binding.llVariantDetailImageList.visibility = View.VISIBLE
                    binding.trVariantDetailCompositeCategoryName.visibility = View.VISIBLE
                    binding.trVariantDetailCompositeBrandName.visibility = View.VISIBLE
                    binding.trVariantDetailCompositeTag.visibility = View.VISIBLE
                    binding.llVariantDetailCompositeDetail.visibility = View.VISIBLE

                    binding.ivVariantDetailVariantImage.visibility = View.GONE
                    binding.crdVariantDetailVariantPackSize.visibility = View.GONE

                    binding.rclvVariantDetailVariantImageList.apply {
                        layoutManager =
                            LinearLayoutManager(
                                applicationContext,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        adapter = ProductImageAdapter(applicationContext, product.productImages)
                    }
                }
                "normal" -> {
                    if (variant.variantPackSize) {
                        binding.trVariantDetailPackSizeCount.visibility = View.VISIBLE

                        binding.crdVariantDetailVariantPackSize.visibility = View.GONE
                    }
                    if (variant.variantImages.isNotEmpty()) {
                        binding.ivVariantDetailVariantImage.visibility = View.VISIBLE

                        binding.llVariantDetailImageList.visibility = View.GONE

                        Glide.with(this).load(variant.variantImages[0].imageFullPath).into(binding.ivVariantDetailVariantImage)
                    } else {
                        binding.llVariantDetailImageList.visibility = View.VISIBLE

                        binding.ivVariantDetailVariantImage.visibility = View.GONE
                    }

                    /*Load list pack_size for variant*/
                    for (i in 0 until product.variants.size) {
                        if (product.variants[i].variantId == variant.variantId) {
                            for (j in 0 until product.variants.size) {
                                if (product.variants[j].variantPackSize) {
                                    if (product.variants[j].variantPackSizeRootId == variant.variantId) {
                                        packSizeList.add(product.variants[j])
                                    }
                                }
                            }
                            binding.rclvVariantDetailPackSizeList.apply {
                                layoutManager = LinearLayoutManager(applicationContext)
                                addItemDecoration(
                                    DividerItemDecoration(
                                        applicationContext,
                                        DividerItemDecoration.VERTICAL
                                    )
                                )
                                adapter = PackSizeAdapter(applicationContext, packSizeList)
                            }
                        }
                    }

                    /*Add option for variant*/
                    if (product.variants.size > 1) {
                        for (i in 0 until product.variants.size) {
                            if (!product.variants[i].variantPackSize) {
                                if (product.variants[i].variantId == variant.variantId) {
                                    if (product.productOption1!="_ _ _"){
                                        val variantOptionTableRow =
                                            LayoutInflater.from(applicationContext)
                                                .inflate(R.layout.item_variant_option, null)
                                        variantOptionTableRow.findViewById<TextView>(R.id.tvVariantOption).text =
                                            product.productOption1
                                        variantOptionTableRow.findViewById<TextView>(R.id.tvVariantOptionValue).text =
                                            product.variants[i].productOption1
                                        binding.tlVariantDetailVariantInfo.addView(variantOptionTableRow)
                                    }
                                    if (product.productOption2!="_ _ _"){
                                        val variantOptionTableRow =
                                            LayoutInflater.from(applicationContext)
                                                .inflate(R.layout.item_variant_option, null)
                                        variantOptionTableRow.findViewById<TextView>(R.id.tvVariantOption).text =
                                            product.productOption2
                                        variantOptionTableRow.findViewById<TextView>(R.id.tvVariantOptionValue).text =
                                            product.variants[i].productOption2
                                        binding.tlVariantDetailVariantInfo.addView(variantOptionTableRow)
                                    }
                                    if (product.productOption3!="_ _ _"){
                                        val variantOptionTableRow =
                                            LayoutInflater.from(applicationContext)
                                                .inflate(R.layout.item_variant_option, null)
                                        variantOptionTableRow.findViewById<TextView>(R.id.tvVariantOption).text =
                                            product.productOption3
                                        variantOptionTableRow.findViewById<TextView>(R.id.tvVariantOptionValue).text =
                                            product.variants[i].productOption3
                                        binding.tlVariantDetailVariantInfo.addView(variantOptionTableRow)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun setMutableLiveData(product: Product, variant: Variant) {
        Handler(Looper.getMainLooper()).postDelayed({
            variantDetailPresenter.product.value = product
            variantDetailPresenter.variant.value = variant
            variantDetailPresenter.txtInventoryOnHand.value =
                getString(R.string.variantDetailActivity1) + NumberFormat.getInstance().format(variant.inventories[0].inventoryOnHand)
            variantDetailPresenter.txtInventoryAvailable.value =
                getString(R.string.variantDetailActivity2) + NumberFormat.getInstance().format(variant.inventories[0].inventoryAvailable)
            variantDetailPresenter.txtInventoryPosition.value =
                variant.inventories[0].inventoryPosition
            variantDetailPresenter.txtBtnDeleteTitle.value =getString(R.string.btnDeleteVariant)
            variantDetailPresenter.txtToolbarTitle.value =getString(R.string.txtTitleVariantDetailActivity)
            variantDetailPresenter.txtVariantSellable.value =
                if (variant.variantSellable) {
                    getString(R.string.productDetailActivity3)
                } else {
                    getString(R.string.variantDetailActivity4)
                }
            variantDetailPresenter.txtVariantTaxable.value =
                if (variant.variantTaxable) {
                    getString(R.string.productDetailActivity5)
                } else {
                    getString(R.string.variantDetailActivity6)
                }
            when(variant.productType){
                "lots_date" -> {
                    variantDetailPresenter.txtProductType.value =
                        getString(R.string.productDetailActivity8)
                    variantDetailPresenter.txtShowProductTypeDetail.value=getString(R.string.productDetailActivity9)
                }
                "serial" -> {
                    variantDetailPresenter.txtProductType.value =
                        getString(R.string.productDetailActivity10)
                    variantDetailPresenter.txtShowProductTypeDetail.value=getString(R.string.productDetailActivity11)
                }
                "composite" -> {
                    variantDetailPresenter.txtBtnDeleteTitle.value =getString(R.string.productDetailActivity12)
                    variantDetailPresenter.txtToolbarTitle.value =getString(R.string.productDetailActivityTitle)
                    variantDetailPresenter.txtCompositeItemCount.value = getString(R.string.productDetailActivity13) + variant.variantCompositeItems.size.toString() + getString(
                        R.string.productDetailActivity14
                    )
                }
                "normal" -> {

                }
            }
            binding.notifyChange()
            binding.varDP = variantDetailPresenter
            binding.ncvVariantDetail.visibility=View.VISIBLE
        },500)
    }

    override fun moveToCompositeItemActivity() {
        val intent = Intent(applicationContext, CompositeItemActivity::class.java)
        intent.putExtra(CompositeItemActivity.KEY_VARIANT, variant)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun getIntentExtra(){
        variant = intent.getParcelableExtra(KEY_VARIANT)!!
    }

    private fun initData(){
        GlobalScope.launch {
            variantDetailPresenter.initData(variant.productId, variant)
        }
        binding.ncvVariantDetail.visibility=View.INVISIBLE
    }
}