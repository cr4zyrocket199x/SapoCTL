package com.cr4zyrocket.sapoctl.presenter.productdetail

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.databinding.ActivityProductDetailBinding
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.presenter.adapter.ProductImageAdapter
import com.cr4zyrocket.sapoctl.presenter.adapter.ProductPriceAdapter
import com.cr4zyrocket.sapoctl.presenter.compositeitem.CompositeItemActivity
import com.cr4zyrocket.sapoctl.presenter.productdetail.adapter.VariantForOneAdapter
import com.cr4zyrocket.sapoctl.presenter.variantdetail.VariantDetailActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat

class ProductDetailActivity : AppCompatActivity(), ProductDetailInterface.ViewModel {
    companion object {
        private const val TAG = "ProductDetailActivity"
        const val KEY_PRODUCT_ID = "productId"
    }

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: Product
    private var productId: Long = -1
    private var variantId: Long = -1
    private lateinit var variantForOneAdapter: VariantForOneAdapter
    private var productDetailPresenter = ProductDetailPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        setSupportActionBar(binding.tbProductDetailToolbar)
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

    override fun showProductDetail(product: Product) {
        Handler(Looper.getMainLooper()).post {
            binding.llProductDetailCompositeDetail.visibility = View.GONE
            binding.rclvProductDetailProductImages.apply {
                adapter = ProductImageAdapter(applicationContext, product.productImages)
            }
            if (product.variants.size == 1) {
                variantId = product.variants[0].variantId
                binding.llProductDetailProductStatus.visibility = View.VISIBLE
                binding.crdProductDetailProductInventory.visibility = View.VISIBLE
                binding.tlProductDetailProductInfo.visibility = View.VISIBLE
                binding.crdProductDetailProductInformationAddition.visibility = View.VISIBLE

                binding.trProductDetailCompositeCategoryName.visibility = View.GONE
                binding.trProductDetailCompositeBrandName.visibility = View.GONE
                binding.trProductDetailCompositeTag.visibility = View.GONE
                binding.crdProductDetailProductVariants.visibility = View.GONE

                when (product.variants[0].productType) {
                    "lots_date" -> {
                        binding.tvProductDetailShowProductTypeDetail.visibility = View.VISIBLE
                        binding.trProductDetailProductType.visibility = View.VISIBLE
                    }
                    "serial" -> {
                        binding.tvProductDetailShowProductTypeDetail.visibility = View.VISIBLE
                        binding.trProductDetailProductType.visibility = View.VISIBLE
                    }
                    "composite" -> {
                        binding.llProductDetailCompositeDetail.visibility = View.VISIBLE
                        binding.trProductDetailCompositeCategoryName.visibility = View.VISIBLE
                        binding.trProductDetailCompositeBrandName.visibility = View.VISIBLE
                        binding.trProductDetailCompositeTag.visibility = View.VISIBLE

                        binding.trProductDetailProductType.visibility = View.GONE
                        binding.tvProductDetailShowProductTypeDetail.visibility = View.GONE
                        binding.crdProductDetailProductInformationAddition.visibility = View.GONE
                    }
                    "normal" -> {
                        binding.trProductDetailProductType.visibility = View.GONE
                        binding.tvProductDetailShowProductTypeDetail.visibility = View.GONE
                    }
                }
                binding.rclvProductDetailProductPriceList.apply {
                    adapter =
                        ProductPriceAdapter(
                            applicationContext,
                            product.variants[0].variantPrices
                        )
                }
            } else {
                binding.crdProductDetailProductVariants.visibility = View.VISIBLE

                binding.llProductDetailProductStatus.visibility = View.GONE
                binding.tlProductDetailProductInfo.visibility = View.GONE
                binding.crdProductDetailProductInventory.visibility = View.GONE
                variantForOneAdapter = VariantForOneAdapter(this,product.variants)
                variantForOneAdapter.onItemClickVariantForOne = { productId, variantId ->
                    val intent = Intent(this, VariantDetailActivity::class.java)
                    intent.putExtra(VariantDetailActivity.KEY_PRODUCT_ID, productId)
                    intent.putExtra(VariantDetailActivity.KEY_VARIANT_ID, variantId)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                binding.rclvProductDetailProductVariantList.apply {
                    addItemDecoration(
                        DividerItemDecoration(
                            applicationContext,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    adapter = variantForOneAdapter
                }
            }
        }
    }

    override fun setMutableLiveData(product: Product) {
        Handler(Looper.getMainLooper()).postDelayed({
            productDetailPresenter.product.value = product
            productDetailPresenter.variantList.value = product.variants
            productDetailPresenter.isActive.value =
                product.productStatus == "active"
            productDetailPresenter.txtBtnDeleteTitle.value =
                getString(R.string.productDetailActivity7)
            productDetailPresenter.txtProductStatus.value = if (product.productStatus == "active") {
                getString(R.string.productDetailActivity1)
            } else {
                getString(R.string.productDetailActivity2)
            }
            productDetailPresenter.txtToolbarTitle.value =
                getString(R.string.txtTitleProductDetailActivity)
            if (product.variants.size == 1) {
                productDetailPresenter.txtProductWeight.value = NumberFormat.getInstance().format(product.variants[0].variantWeightValue).toString()
                productDetailPresenter.variant.value = product.variants[0]
                productDetailPresenter.txtInventoryOnHand.value =
                    getString(R.string.variantDetailActivity1) + NumberFormat.getInstance().format(product.variants[0].inventories[0].inventoryOnHand)
                productDetailPresenter.txtInventoryAvailable.value =
                    getString(R.string.variantDetailActivity2) + NumberFormat.getInstance().format(product.variants[0].inventories[0].inventoryAvailable)
                productDetailPresenter.txtInventoryPosition.value =
                    product.variants[0].inventories[0].inventoryPosition
                productDetailPresenter.txtVariantSellable.value =
                    if (product.variants[0].variantSellable) {
                        getString(R.string.productDetailActivity3)
                    } else {
                        getString(R.string.variantDetailActivity4)
                    }
                productDetailPresenter.txtVariantTaxable.value =
                    if (product.variants[0].variantTaxable) {
                        getString(R.string.productDetailActivity5)
                    } else {
                        getString(R.string.variantDetailActivity6)
                    }

                when (product.variants[0].productType) {
                    "composite" -> {
                        productDetailPresenter.txtBtnDeleteTitle.value =
                            getString(R.string.productDetailActivity12)
                        productDetailPresenter.txtToolbarTitle.value =
                            getString(R.string.productDetailActivityTitle)
                        productDetailPresenter.txtCompositeItemCount.value =
                            getString(R.string.productDetailActivity13) + product.variants[0].variantCompositeItems.size.toString() + getString(
                                R.string.productDetailActivity14
                            )
                    }
                    "lots_date" -> {
                        productDetailPresenter.txtProductType.value =
                            getString(R.string.productDetailActivity8)
                        productDetailPresenter.txtShowProductTypeDetail.value =
                            getString(R.string.productDetailActivity9)
                    }
                    "serial" -> {
                        productDetailPresenter.txtProductType.value =
                            getString(R.string.productDetailActivity10)
                        productDetailPresenter.txtShowProductTypeDetail.value =
                            getString(R.string.productDetailActivity11)
                    }
                }
            }
            binding.proDP = productDetailPresenter
            binding.notifyChange()
            binding.ncvProductDetail.visibility = View.VISIBLE
        }, 500)
    }

    override fun moveToCompositeItemActivity() {
        val intent = Intent(this, CompositeItemActivity::class.java)
        intent.putExtra(CompositeItemActivity.KEY_PRODUCT_ID, productId)
        intent.putExtra(CompositeItemActivity.KEY_VARIANT_ID, variantId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun getIntentExtra(){
        productId = intent.getLongExtra(KEY_PRODUCT_ID, 0L)
    }

    private fun initData(){
        GlobalScope.launch {
            productDetailPresenter.initData(productId)
        }
        binding.ncvProductDetail.visibility = View.INVISIBLE
    }
}