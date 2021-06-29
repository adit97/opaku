package com.opaku.id.ui.offerscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.opaku.id.R
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.ui.recyclerview.GeneralRecyclerViewAdapter
import com.opaku.id.core.ui.recyclerview.GridSpacingItemDecoration
import com.opaku.id.databinding.ActivityOfferScreenBinding
import com.opaku.id.ui.detailproduct.DetailProductActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class OfferScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfferScreenBinding
    private val viewModel: OfferScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfferScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupToolbar()
        setupBinding()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.apply {
        }
    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = this@OfferScreenActivity
            vm = viewModel

            rvFlashSaleProduct.apply {
                addItemDecoration(GridSpacingItemDecoration(2, 32, false))
                isNestedScrollingEnabled = false
                adapter = GeneralRecyclerViewAdapter<ProductModel>(
                    layoutId = R.layout.item_product_grid,
                    onClickListener = { model, position ->
                        startActivity(Intent(this@OfferScreenActivity, DetailProductActivity::class.java))
                    },
                    onLongClickListener = { position ->

                    }
                )
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}