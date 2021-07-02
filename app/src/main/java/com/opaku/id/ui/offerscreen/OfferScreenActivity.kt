package com.opaku.id.ui.offerscreen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.opaku.id.R
import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.BannerModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.ui.recyclerview.GeneralRecyclerViewAdapter
import com.opaku.id.core.ui.recyclerview.GridSpacingItemDecoration
import com.opaku.id.core.utils.Constant.BANNER
import com.opaku.id.core.utils.Constant.PRODUCT
import com.opaku.id.databinding.ActivityOfferScreenBinding
import com.opaku.id.ui.detailproduct.DetailProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfferScreenBinding
    private val viewModel: OfferScreenViewModel by viewModels()

    private var data: BannerModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfferScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupToolbar()
        setupViewModel()
        setupBinding()
    }

    private fun setupViewModel() {
        data = intent.getParcelableExtra(BANNER)

        viewModel.apply {
            viewModel.getProducts.observe(this@OfferScreenActivity, {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        viewModel.promotionProductList.value = it.data
                    }
                    is Resource.Error -> {
                    }
                }
            })
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
                        startActivity(
                            Intent(
                                this@OfferScreenActivity,
                                DetailProductActivity::class.java
                            ).putExtra(PRODUCT, model)
                        )
                    },
                    onLongClickListener = { position ->

                    }
                )
            }
        }

        if (data != null) {
            binding.tvTitle.text = data!!.title

            object : CountDownTimer(data!!.time, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val hours = millisUntilFinished / (60 * 60 * 1000) % 24
                    val minutes = millisUntilFinished / (60 * 1000) % 60
                    val seconds = millisUntilFinished / 1000 % 60

                    binding.tvHour.text = hours.toString()
                    binding.tvMinute.text = minutes.toString()
                    binding.tvSecond.text = seconds.toString()
                }

                override fun onFinish() {}

            }.start()
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