package com.opaku.id.ui.detailproduct

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.opaku.id.R
import com.opaku.id.core.domain.model.ProductColorModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.model.ProductVariantModel
import com.opaku.id.core.ui.recyclerview.GeneralRecyclerViewAdapter
import com.opaku.id.core.ui.recyclerview.HorizontalSpacingItemDecoration
import com.opaku.id.core.ui.viewpager.GeneralViewPagerAdapter
import com.opaku.id.databinding.ActivityDetailProductBinding
import com.opaku.id.ui.dashboard.fragment.home.fragment.BannerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    private val viewModel: DetailProductViewModel by viewModels()

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupBinding()
        setupToolbar()
        setupViewModel()
        setupAnalytic()
        setupOnClickEvent()
    }

    private fun setupOnClickEvent() {
        binding.ivFavorite.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, "SKU_123 ${System.currentTimeMillis()}")
                putString(FirebaseAnalytics.Param.ITEM_NAME, "jeggings")
                putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "pants")
                putString(FirebaseAnalytics.Param.ITEM_VARIANT, "black")
                putString(FirebaseAnalytics.Param.ITEM_BRAND, "Google")
                putDouble(FirebaseAnalytics.Param.PRICE, 9.99)
            })
        }
    }

    private fun setupAnalytic() {
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, "SKU_123 ${System.currentTimeMillis()}")
            putString(FirebaseAnalytics.Param.ITEM_NAME, "jeggings")
            putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "pants")
            putString(FirebaseAnalytics.Param.ITEM_VARIANT, "black")
            putString(FirebaseAnalytics.Param.ITEM_BRAND, "Google")
            putDouble(FirebaseAnalytics.Param.PRICE, 9.99)
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

    private fun setupViewModel() {
        viewModel.apply {
            productSizeList.value = getProductSizeList()
            productColorList.value = getProductColorList()
            productRecommendedList.value = getRecommendedProduct()
        }
    }

    private fun getProductColorList(): List<ProductColorModel> = listOf(
        ProductColorModel("#FF40BFFF", true),
        ProductColorModel("#FFFFC833"),
        ProductColorModel("#FFFFC833"),
        ProductColorModel("#FF53D1B6"),
        ProductColorModel("#FF5C61F4"),
        ProductColorModel("#FF223263")
    )

    private fun getProductSizeList(): List<ProductVariantModel> = listOf(
        ProductVariantModel("21", true),
        ProductVariantModel("22"),
        ProductVariantModel("23"),
        ProductVariantModel("24"),
        ProductVariantModel("25"),
        ProductVariantModel("26")
    )

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = this@DetailProductActivity
            vm = viewModel

            vpProductDetail.adapter = getProductDetailVpAdapter()

            rvProductSize.apply {
                addItemDecoration(HorizontalSpacingItemDecoration(32))
                adapter = GeneralRecyclerViewAdapter<ProductVariantModel>(
                    layoutId = R.layout.item_product_size,
                    onClickListener = { model, position ->
                        viewModel.setProductSizeSelected(position)
                    },
                    onLongClickListener = { position ->

                    }
                )
            }

            rvProductColor.apply {
                addItemDecoration(HorizontalSpacingItemDecoration(32))
                adapter = GeneralRecyclerViewAdapter<ProductColorModel>(
                    layoutId = R.layout.item_product_color,
                    onClickListener = { model, position ->
                        viewModel.setProductColorSelected(position)
                    },
                    onLongClickListener = { position ->

                    }
                )
            }

            rvRecommendedProduct.apply {
                addItemDecoration(HorizontalSpacingItemDecoration(32))
                adapter = GeneralRecyclerViewAdapter<ProductModel>(
                    layoutId = R.layout.item_product,
                    onClickListener = { model, position ->

                    },
                    onLongClickListener = { position ->

                    }
                )
            }

        }
    }

    private fun getProductDetailVpAdapter() = GeneralViewPagerAdapter(
        this, listOf(
            BannerFragment.newInstance(R.drawable.promotion_image_1),
            BannerFragment.newInstance(R.drawable.promotion_image_2),
        )
    )

    private fun getRecommendedProduct(): List<ProductModel> = listOf()

}