package com.cr4zyrocket.sapoctl.presenter.compositeitem

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
import com.cr4zyrocket.sapoctl.databinding.ActivityCompositeItemBinding
import com.cr4zyrocket.sapoctl.model.Variant
import com.cr4zyrocket.sapoctl.presenter.compositeitem.adapter.CompositeItemAdapter
import com.cr4zyrocket.sapoctl.presenter.variantdetail.VariantDetailActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class CompositeItemActivity : AppCompatActivity(), CompositeItemInterface.ViewModel {
    companion object {
        private const val TAG = "CompositeItemActivity"
        const val KEY_VARIANT_ID = "variantId"
        const val KEY_PRODUCT_ID = "productId"
    }

    private lateinit var binding: ActivityCompositeItemBinding
    private var productId: Long = -1
    private var variantId: Long = -1
    private lateinit var compositeItemAdapter: CompositeItemAdapter
    private var compositeItemPresenter = CompositeItemPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_composite_item)
        setSupportActionBar(binding.tbCompositeItemToolbar)
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

    override fun showCompositeSubItemList(
        variant: Variant,
        compositeSubItemList: MutableList<Variant>
    ) {
        compositeItemAdapter =
            CompositeItemAdapter(this, variant.variantCompositeItems, compositeSubItemList)
        compositeItemAdapter.onItemClickCompositeItem = { productId, variantId ->
            val intent = Intent(applicationContext, VariantDetailActivity::class.java)
            intent.putExtra(VariantDetailActivity.KEY_PRODUCT_ID, productId)
            intent.putExtra(VariantDetailActivity.KEY_VARIANT_ID, variantId)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        Handler(Looper.getMainLooper()).post {
            binding.rclvCompositeItemList.apply {
                addItemDecoration(
                    DividerItemDecoration(
                        applicationContext,
                        DividerItemDecoration.VERTICAL
                    )
                )
                adapter = compositeItemAdapter
            }
        }
    }

    override fun setMutableLiveData(variant: Variant) {
        var totalPrice = 0L
        variant.variantCompositeItems.forEach {
            totalPrice += it.compositeSubItemPrice
        }
        Handler(Looper.getMainLooper()).postDelayed({
            compositeItemPresenter.txtCompositeSubItemsCount.value =
                getString(R.string.total) + variant.variantCompositeItems.size.toString()
            compositeItemPresenter.txtCompositeSubItemsPrice.value =
                NumberFormat.getInstance(Locale.US).format(totalPrice)
            binding.comItemP = compositeItemPresenter
            binding.notifyChange()
            binding.rlCompositeItem.visibility = View.VISIBLE
        }, 500)
    }

    private fun getIntentExtra() {
        productId = intent.getLongExtra(KEY_PRODUCT_ID,0)
        variantId = intent.getLongExtra(KEY_VARIANT_ID,0)
    }

    private fun initData() {
        GlobalScope.launch {
            compositeItemPresenter.initData(productId, variantId)
        }
        binding.rlCompositeItem.visibility = View.INVISIBLE
    }
}