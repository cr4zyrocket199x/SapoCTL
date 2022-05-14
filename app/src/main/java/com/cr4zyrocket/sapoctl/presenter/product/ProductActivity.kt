package com.cr4zyrocket.sapoctl.presenter.product

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
import com.cr4zyrocket.sapoctl.presenter.adapter.ProductAdapter
import com.cr4zyrocket.sapoctl.presenter.adapter.VariantAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity(), ProductInterface.ViewModel {
    companion object {
        private const val TAG = "ProductActivity"
        private const val SHARED_PREF_NAME = "Product_Info"
        private const val SHARED_PREF_IS_PRODUCT_RESULT = "isProductResult"
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
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
    var keySearch: String = ""

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
        if (menu != null) {
            if (isProductResult) {
                menu.getItem(0).setIcon(R.drawable.ic_warehouse)
            } else {
                menu.getItem(0).setIcon(R.drawable.ic_product)
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
                if (isProductResult) {
                    item.setIcon(R.drawable.ic_product)
                } else {
                    item.setIcon(R.drawable.ic_warehouse)
                }
                isProductResult = !isProductResult
                currentPage = 1
                GlobalScope.launch {
                    productPresenter.initData(isProductResult, currentPage)
                }
                binding.rclvProductList.visibility = View.INVISIBLE
                pref.edit().putBoolean(SHARED_PREF_IS_PRODUCT_RESULT, isProductResult).apply()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setRefresh(isRefresh: Boolean) {
        binding.srlProductRefresh.isRefreshing = isRefresh
    }

    override fun showProductList(productList: MutableList<Product>) {
        this.productList = productList
        Handler(Looper.getMainLooper()).post {
            adapterProduct = ProductAdapter(applicationContext, productList)
            binding.rclvProductList.adapter = adapterProduct
        }

    }

    override fun showVariantList(variantList: MutableList<Variant>) {
        this.variantList = variantList
        Handler(Looper.getMainLooper()).post {
            adapterVariant = VariantAdapter(applicationContext, variantList)
            binding.rclvProductList.adapter = adapterVariant
        }
    }

    override fun setMutableLiveData(meta: Meta) {
        Handler(Looper.getMainLooper()).postDelayed({
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
            binding.notifyChange()
            binding.proP = productPresenter
            binding.rclvProductList.visibility = View.VISIBLE
        }, 500)
    }

    private fun setUpSearchView() {
        binding.svProductSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    keySearch = p0
                    if (isProductResult) {
                        GlobalScope.launch {
                            productList =
                                productPresenter.getProductList(currentPage, p0)
                            Handler(Looper.getMainLooper()).postDelayed({
                                adapterProduct =
                                    ProductAdapter(applicationContext, productList)
                                binding.rclvProductList.adapter = adapterProduct
                            }, 500)
                        }
                    } else {
                        GlobalScope.launch {
                            variantList =
                                productPresenter.getVariantList(currentPage, p0)
                            Handler(Looper.getMainLooper()).postDelayed({
                                adapterVariant =
                                    VariantAdapter(applicationContext, variantList)
                                binding.rclvProductList.adapter = adapterVariant
                            }, 500)
                        }
                    }
                    if (p0.isEmpty()) {
                        currentPage = 1
                    }
                }
                return true
            }
        })

    }

    private fun setUpRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        binding.rclvProductList.apply {
            layoutManager = linearLayoutManager
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
                val total: Int? = binding.rclvProductList.adapter?.itemCount
                if (!isLoadMore && currentPage < totalPage) {
                    if (total != null) {
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (total - 1)) {
                            currentPage++
                            if (isProductResult) {
                                GlobalScope.launch {
                                    if (currentPage == 1L) {
                                        productList =
                                            productPresenter.getProductList(currentPage, keySearch)
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            adapterProduct =
                                                ProductAdapter(applicationContext, productList)
                                            binding.rclvProductList.adapter = adapterProduct
                                        }, 500)
                                    } else {
                                        productList.addAll(
                                            productPresenter.getProductList(
                                                currentPage,
                                                keySearch
                                            )
                                        )
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            adapterProduct.notifyItemInserted(productList.size - 1)
                                        }, 500)
                                    }
                                }
                            } else {
                                GlobalScope.launch {
                                    if (currentPage == 1L) {
                                        variantList =
                                            productPresenter.getVariantList(currentPage, keySearch)
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            adapterVariant =
                                                VariantAdapter(applicationContext, variantList)
                                            binding.rclvProductList.adapter = adapterVariant
                                        }, 500)
                                    } else {
                                        variantList.addAll(
                                            productPresenter.getVariantList(
                                                currentPage,
                                                keySearch
                                            )
                                        )
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            adapterVariant.notifyItemInserted(variantList.size - 1)
                                        }, 500)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setUpRefresh() {
        binding.srlProductRefresh.setOnRefreshListener {
            isLoadMore = true
            binding.srlProductRefresh.isRefreshing = true
            if (isProductResult) {
                GlobalScope.launch {
                    productList =
                        productPresenter.getProductList(currentPage, keySearch)
                    Handler(Looper.getMainLooper()).postDelayed({
                        adapterProduct =
                            ProductAdapter(applicationContext, productList)
                        binding.rclvProductList.adapter = adapterProduct
                    }, 500)
                    binding.srlProductRefresh.isRefreshing = false
                }
            } else {
                GlobalScope.launch {
                    variantList =
                        productPresenter.getVariantList(currentPage, keySearch)
                    Handler(Looper.getMainLooper()).postDelayed({
                        adapterVariant =
                            VariantAdapter(applicationContext, variantList)
                        binding.rclvProductList.adapter = adapterVariant
                    }, 500)
                }
                binding.srlProductRefresh.isRefreshing = false
            }
            isLoadMore = false
            currentPage = 1
        }
    }

    private fun setUpSharedPreferences() {
        pref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        isProductResult = pref.getBoolean(SHARED_PREF_IS_PRODUCT_RESULT, true)
    }

    private fun initData() {
        GlobalScope.launch {
            productPresenter.initData(isProductResult, currentPage)
        }
        binding.rclvProductList.visibility = View.INVISIBLE
    }
}