package com.cr4zyrocket.sapoctl.product

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.adapter.ProductAdapter
import com.cr4zyrocket.sapoctl.adapter.VariantAdapter
import com.cr4zyrocket.sapoctl.databinding.ActivityProductBinding
import com.cr4zyrocket.sapoctl.model.Meta
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.model.Variant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity(), ProductInterface.ViewModel {
    companion object {
        private const val TAG = "ProductActivity"
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapterVariant: VariantAdapter
    private lateinit var adapterProduct: ProductAdapter
    private lateinit var binding: ActivityProductBinding
    private lateinit var meta: Meta
    private var productList = mutableListOf<Product>()
    private var variantList = mutableListOf<Variant>()
    private var isProductResult = true
    private var productPresenter = ProductPresenter(this)
    private var currentPage: Long = 1
    private var totalPage: Long = 1
    private var isLoadMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbProductToolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
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

        binding.svProductSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (isProductResult) {
                    adapterProduct.filter.filter(p0)
                } else {
                    adapterVariant.filter.filter(p0)
                }
                return true
            }
        })

        binding.srlProductRefresh.setOnRefreshListener {
            isLoadMore=true
            if (binding.svProductSearch.query.isNotEmpty()) {
                if (isProductResult) {
                    adapterProduct.filter.filter(binding.svProductSearch.query)
                } else {
                    adapterVariant.filter.filter(binding.svProductSearch.query)
                }
                binding.srlProductRefresh.isRefreshing = false
            } else {
                binding.srlProductRefresh.isRefreshing = true
                GlobalScope.launch {
                    productPresenter.initData(isProductResult,currentPage)
                }
                isLoadMore=false
                currentPage = 1
            }
        }

        /*LoadMore recyclerView*/
        binding.rclvProductList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val total: Int? = binding.rclvProductList.adapter?.itemCount
                if (!isLoadMore && currentPage < totalPage) {
                    if (total != null) {
                        if (binding.svProductSearch.query.isEmpty()) {
                            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (total - 1)) {
                                currentPage++
                                if (isProductResult) {
                                    GlobalScope.launch {
                                        if (currentPage == 1L) {
                                            productList =
                                                productPresenter.getProductList(currentPage)
                                            Handler(Looper.getMainLooper()).postDelayed({
                                                adapterProduct =
                                                    ProductAdapter(applicationContext, productList)
                                                binding.rclvProductList.adapter = adapterProduct
                                            }, 500)
                                        } else {
                                            productList.addAll(
                                                productPresenter.getProductList(
                                                    currentPage
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
                                                productPresenter.getVariantList(currentPage)
                                            Handler(Looper.getMainLooper()).postDelayed({
                                                adapterVariant =
                                                    VariantAdapter(applicationContext, variantList)
                                                binding.rclvProductList.adapter = adapterVariant
                                            }, 500)
                                        } else {
                                            variantList.addAll(
                                                productPresenter.getVariantList(
                                                    currentPage
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
            }
        })

        GlobalScope.launch {
            productPresenter.initData(isProductResult, currentPage)
        }
        binding.rclvProductList.visibility=View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_appbar_product, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.menuAppBarProductChangeResult -> {
                if (isProductResult){
                    item.icon=getDrawable(R.drawable.ic_product)
                }else{
                    item.icon=getDrawable(R.drawable.ic_warehouse)
                }
                isProductResult = !isProductResult
                currentPage = 1
                GlobalScope.launch {
                    productPresenter.initData(isProductResult, currentPage)
                }
                binding.rclvProductList.visibility=View.INVISIBLE
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setRefresh(isRefresh: Boolean) {
        binding.srlProductRefresh.isRefreshing = isRefresh
    }

    override fun showProductList(productList: MutableList<Product>) {
        this.productList = productList
        Handler(Looper.getMainLooper()).post{
            adapterProduct = ProductAdapter(applicationContext, productList)
            binding.rclvProductList.adapter = adapterProduct
        }

    }

    override fun showVariantList(variantList: MutableList<Variant>) {
        this.variantList = variantList
        Handler(Looper.getMainLooper()).post{
            adapterVariant = VariantAdapter(applicationContext, variantList)
            binding.rclvProductList.adapter = adapterVariant
        }
    }

    override fun setMutableLiveData(meta: Meta) {
        Handler(Looper.getMainLooper()).postDelayed( {
            this.meta = meta
            totalPage =
                if (meta.metaTotal % meta.metaLimit == 0L) {
                    meta.metaTotal / meta.metaLimit
                } else {
                    meta.metaTotal / meta.metaLimit + 1
                }
            if (isProductResult){
                productPresenter.txtProductTitle.value =
                    getString(R.string.productActivity3)
                productPresenter.txtProductCount.value =
                    meta.metaTotal.toString() + getString(R.string.productActivity2)
            }else{
                productPresenter.txtProductTitle.value =
                    getString(R.string.productActivity1)
                productPresenter.txtProductCount.value =
                    meta.metaTotal.toString() + getString(R.string.productActivity4)
            }
            binding.proP=productPresenter
            binding.rclvProductList.visibility=View.VISIBLE
        },500)
    }
}