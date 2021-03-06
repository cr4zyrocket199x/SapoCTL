package com.cr4zyrocket.sapoctl.presenter.product

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.databinding.ActivityProductBinding
import com.cr4zyrocket.sapoctl.model.Meta
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.model.Variant
import com.cr4zyrocket.sapoctl.presenter.product.adapter.ProductAdapter
import com.cr4zyrocket.sapoctl.presenter.product.adapter.VariantAdapter
import com.cr4zyrocket.sapoctl.presenter.productdetail.ProductDetailActivity
import com.cr4zyrocket.sapoctl.presenter.variantdetail.VariantDetailActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProductActivity : AppCompatActivity(), ProductInterface.ViewModel {
    companion object {
        private const val TAG = "ProductActivity"
        private const val SHARED_PREF_NAME = "Product_Info"
        private const val SHARED_PREF_IS_PRODUCT_RESULT = "isProductResult"
    }

    private lateinit var adapterVariant: VariantAdapter
    private lateinit var adapterProduct: ProductAdapter
    private lateinit var binding: ActivityProductBinding
    private lateinit var meta: Meta
    private lateinit var pref: SharedPreferences
    private var productList = mutableListOf<Product>()
    private var variantList = mutableListOf<Variant>()
    private var isProductResult = true
    private var productPresenter = ProductPresenter(this)
    private var currentPage: Long = 1
    private var totalPage: Long = 1
    private var isLoadMore = false
    private var keySearch: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        setSupportActionBar(binding.tbProductToolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        setUpSharedPreferences()

        setUpRecyclerView()

        setUpSearchView()

        setUpRefresh()

        initData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_appbar_product, menu)
        menu?.let {
            if (isProductResult) {
                it.getItem(0).setIcon(R.drawable.ic_warehouse)
            } else {
                it.getItem(0).setIcon(R.drawable.ic_product)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.menuAppBarProductChangeResult -> {
                binding.pbProduct.visibility = View.VISIBLE
                if (isProductResult) {
                    item.setIcon(R.drawable.ic_product)
                } else {
                    item.setIcon(R.drawable.ic_warehouse)
                }
                isProductResult = !isProductResult
                initData()
                pref.edit().putBoolean(SHARED_PREF_IS_PRODUCT_RESULT, isProductResult).apply()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setRefresh(isRefresh: Boolean) {
        binding.srlProductRefresh.isRefreshing = isRefresh
    }

    override fun showProductList() {
        initProductAdapter()
    }

    override fun showVariantList() {
        initVariantAdapter()
    }

    override fun setMutableLiveData(meta: Meta) {
        Handler(Looper.getMainLooper()).post {
            this.meta = meta
            totalPage =
                if (meta.metaTotal % meta.metaLimit == 0L) {
                    meta.metaTotal / meta.metaLimit
                } else {
                    meta.metaTotal / meta.metaLimit + 1
                }
            if (isProductResult) {
                productPresenter.txtProductTitle.value =
                    getString(R.string.productActivity3)
                productPresenter.txtProductCount.value =
                    meta.metaTotal.toString() + getString(R.string.productActivity4)
            } else {
                productPresenter.txtProductTitle.value =
                    getString(R.string.productActivity1)
                productPresenter.txtProductCount.value =
                    meta.metaTotal.toString() + getString(R.string.productActivity2)
            }
            binding.proP = productPresenter
            binding.notifyChange()
        }
    }

    private fun setUpSearchView() {
        binding.svProductSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                currentPage = 1
                binding.pbProduct.visibility = View.VISIBLE
                keySearch = p0 ?: ""
                if (isProductResult) {
                    initProductAdapter()
                } else {
                    initVariantAdapter()
                }
                if (keySearch.isEmpty()) {
                    currentPage = 1
                }
                return true
            }
        })
    }

    private fun setUpRecyclerView() {
        binding.rclvProductList.apply {
            addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        /*LoadMore recyclerView*/
        binding.rclvProductList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val total = binding.rclvProductList.adapter?.itemCount ?: 0
                if (!isLoadMore && currentPage < totalPage && total > 0 && (binding.rclvProductList.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == (total - 1)) {
                    currentPage++
                    if (isProductResult) {
                        GlobalScope.launch {
                            productList.addAll(
                                productPresenter.getProductList(
                                    currentPage,
                                    keySearch
                                )
                            )
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            adapterProduct.notifyItemInserted(productList.size - 1)
                        }, 500)
                    } else {
                        GlobalScope.launch {
                            variantList.addAll(
                                productPresenter.getVariantList(
                                    currentPage,
                                    keySearch
                                )
                            )
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            adapterVariant.notifyItemInserted(variantList.size - 1)
                        }, 500)
                    }
                }
            }
        })
    }

    private fun setUpRefresh() {
        binding.srlProductRefresh.setOnRefreshListener {
            binding.srlProductRefresh.isRefreshing = true
            currentPage = 1
            if (isProductResult) {
                initProductAdapter()
                binding.srlProductRefresh.isRefreshing = false
            } else {
                initVariantAdapter()
                binding.srlProductRefresh.isRefreshing = false
            }
        }
    }

    private fun setUpSharedPreferences() {
        pref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        isProductResult = pref.getBoolean(SHARED_PREF_IS_PRODUCT_RESULT, true)
    }

    private fun initData() {
        currentPage = 1
        binding.svProductSearch.setQuery("", false)
        keySearch = ""
        binding.svProductSearch.clearFocus()
        GlobalScope.launch {
            productPresenter.initData(isProductResult, currentPage)
        }
        binding.rclvProductList.visibility = View.INVISIBLE
    }

    private fun initProductAdapter() {
        GlobalScope.launch {
            productList = productPresenter.getProductList(currentPage, keySearch)
            Handler(Looper.getMainLooper()).postDelayed({
                adapterProduct =
                    ProductAdapter(applicationContext, productList)
                adapterProduct.onItemClickProduct = {
                    val intent = Intent(applicationContext, ProductDetailActivity::class.java)
                    intent.putExtra(ProductDetailActivity.KEY_PRODUCT_ID, it)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                binding.rclvProductList.adapter = adapterProduct
                binding.rclvProductList.visibility = View.VISIBLE
                binding.pbProduct.visibility = View.VISIBLE
                binding.pbProduct.visibility = View.INVISIBLE
            }, 500)
        }
    }

    private fun initVariantAdapter() {
        GlobalScope.launch {
            variantList = productPresenter.getVariantList(currentPage, keySearch)
            Handler(Looper.getMainLooper()).postDelayed({
                adapterVariant =
                    VariantAdapter(applicationContext, variantList)
                adapterVariant.onItemClickVariant = { productId, variantId ->
                    val intent = Intent(applicationContext, VariantDetailActivity::class.java)
                    intent.putExtra(VariantDetailActivity.KEY_PRODUCT_ID, productId)
                    intent.putExtra(VariantDetailActivity.KEY_VARIANT_ID, variantId)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                binding.rclvProductList.adapter = adapterVariant
                binding.rclvProductList.visibility = View.VISIBLE
                binding.pbProduct.visibility = View.INVISIBLE
            }, 500)
        }
    }
}