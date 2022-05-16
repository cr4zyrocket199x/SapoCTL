package com.cr4zyrocket.sapoctl.presenter.composite_item

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cr4zyrocket.sapoctl.R
import com.cr4zyrocket.sapoctl.databinding.ActivityCompositeItemBinding
import com.cr4zyrocket.sapoctl.model.Product
import com.cr4zyrocket.sapoctl.model.Variant
import com.cr4zyrocket.sapoctl.presenter.adapter.CompositeItemAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class CompositeItemActivity : AppCompatActivity(), CompositeItemInterface.ViewModel {
    companion object {
        private const val TAG = "CompositeItemActivity"
        const val KEY_VARIANT = "variant"
    }

    private lateinit var binding: ActivityCompositeItemBinding
    private lateinit var variant: Variant
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

    override fun showCompositeSubItemList(variant: Variant, compositeSubItemList: MutableList<Variant>) {
        Handler(Looper.getMainLooper()).post {
            binding.rclvCompositeItemList.apply {
                layoutManager=LinearLayoutManager(applicationContext)
                addItemDecoration(
                    DividerItemDecoration(
                        applicationContext,
                        DividerItemDecoration.VERTICAL
                    )
                )
                adapter=CompositeItemAdapter(applicationContext,variant.variantCompositeItems,compositeSubItemList)
            }
        }
    }

    override fun setMutableLiveData(variant: Variant) {
        Handler(Looper.getMainLooper()).postDelayed({
            var totalPrice = 0L
            compositeItemPresenter.txtCompositeSubItemsCount.value =
                getString(R.string.total) + variant.variantCompositeItems.size.toString()
            variant.variantCompositeItems.forEach {
                totalPrice += it.compositeSubItemPrice
            }
            compositeItemPresenter.txtCompositeSubItemsPrice.value =
                NumberFormat.getInstance(Locale.US).format(totalPrice)
            binding.notifyChange()
            binding.comItemP = compositeItemPresenter
            binding.rlCompositeItem.visibility=View.VISIBLE
        }, 500)
    }

    private fun getIntentExtra(){
        variant = intent.getParcelableExtra(KEY_VARIANT)!!
    }

    private fun initData(){
        GlobalScope.launch {
            compositeItemPresenter.initData(variant)
        }
        binding.rlCompositeItem.visibility=View.INVISIBLE
    }
}