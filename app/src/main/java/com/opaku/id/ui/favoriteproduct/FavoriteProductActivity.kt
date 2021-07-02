package com.opaku.id.ui.favoriteproduct

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.opaku.id.R
import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.ui.recyclerview.GeneralRecyclerViewAdapter
import com.opaku.id.core.ui.recyclerview.GridSpacingItemDecoration
import com.opaku.id.core.utils.Constant.PRODUCT
import com.opaku.id.databinding.ActivityFavoriteProductBinding
import com.opaku.id.ui.detailproduct.DetailProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteProductBinding

    private val viewModel: FavoriteProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupToolbar()
        setupBinding()
        setupViewModel()
    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = this@FavoriteProductActivity
            vm = viewModel

            rvFavoriteProduct.apply {
                addItemDecoration(GridSpacingItemDecoration(2, 32, false))
                isNestedScrollingEnabled = false
                adapter = GeneralRecyclerViewAdapter<ProductModel>(
                    layoutId = R.layout.item_product_grid,
                    onClickListener = { model, position ->
                        startActivity(
                            Intent(
                                this@FavoriteProductActivity,
                                DetailProductActivity::class.java
                            ).putExtra(PRODUCT, model)
                        )
                    },
                    onLongClickListener = { position ->

                    }
                )
            }
        }
    }

    private fun setupViewModel() {
        viewModel.getFavoriteProducts.observe(this, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    viewModel.favoriteProduct.observe(this, { fp ->
                        val finalData = it.data?.filter { pm ->
                            fp.contains(pm.id)
                        }
                        viewModel.favoriteProductList.value = finalData
                    })
                }
                is Resource.Error -> {
                }
            }
        })
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