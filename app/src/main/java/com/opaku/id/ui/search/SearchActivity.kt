package com.opaku.id.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.opaku.id.R
import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.FilterModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.ui.recyclerview.GeneralRecyclerViewAdapter
import com.opaku.id.core.ui.recyclerview.GridSpacingItemDecoration
import com.opaku.id.core.utils.Constant
import com.opaku.id.core.utils.Constant.FILTER
import com.opaku.id.core.utils.toDp
import com.opaku.id.databinding.ActivitySearchBinding
import com.opaku.id.ui.detailproduct.DetailProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupViewModel()
        setupBinding()
    }

    private fun setupViewModel() {
        viewModel.filterModel.value = intent.getParcelableExtra(FILTER)
        println(viewModel.filterModel.value)
        viewModel.filterProduct.observe(this, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    viewModel.productAfterFilter.value = it.data
                }
                is Resource.Error -> {
                }
            }
        })
    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = this@SearchActivity
            vm = viewModel

            rvSearch.apply {
                addItemDecoration(GridSpacingItemDecoration(2, toDp(16), false))
                adapter = GeneralRecyclerViewAdapter<ProductModel>(
                    layoutId = R.layout.item_product_grid,
                    onClickListener = { model, position ->
                        startActivity(
                            Intent(
                                this@SearchActivity,
                                DetailProductActivity::class.java
                            ).putExtra(Constant.PRODUCT, model)
                        )
                    },
                    onLongClickListener = { position ->

                    }
                )
            }
        }
    }
}