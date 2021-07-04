package com.opaku.id.ui.detailproduct

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.opaku.id.R
import com.opaku.id.core.data.Resource
import com.opaku.id.core.data.source.local.session.ISessionManager
import com.opaku.id.core.domain.model.*
import com.opaku.id.core.ui.recyclerview.GeneralRecyclerViewAdapter
import com.opaku.id.core.ui.recyclerview.HorizontalSpacingItemDecoration
import com.opaku.id.core.ui.viewpager.GeneralViewPagerAdapter
import com.opaku.id.core.utils.ButtonClickListener
import com.opaku.id.core.utils.Constant.PRODUCT
import com.opaku.id.core.utils.toast
import com.opaku.id.databinding.ActivityDetailProductBinding
import com.opaku.id.ui.detailproduct.fragment.PreviewFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: ISessionManager

    private lateinit var binding: ActivityDetailProductBinding

    private val viewModel: DetailProductViewModel by viewModels()

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val relatedProductAnalytic = mutableListOf<Bundle>()

    private val bundleProduct = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initView() {
        setupToolbar()
        setupViewModel()
        setupBinding()
        setupAnalytic()
    }

    private fun setupAnalytic() {
        val getCategory = resources.getStringArray(R.array.category).toList()

        firebaseAnalytics = Firebase.analytics

        bundleProduct.apply {
            putString(
                FirebaseAnalytics.Param.ITEM_ID,
                viewModel.product.value?.id
            )
            putString(
                FirebaseAnalytics.Param.ITEM_NAME,
                viewModel.product.value?.name
            )
            putString(
                FirebaseAnalytics.Param.ITEM_CATEGORY,
                getCategory[viewModel.product.value?.category!!]
            )
            putString(
                FirebaseAnalytics.Param.ITEM_BRAND,
                viewModel.product.value!!.brand
            )
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, Bundle().apply {
            bundleProduct.apply {
                putString(
                    FirebaseAnalytics.Param.ITEM_VARIANT,
                    viewModel.product.value!!.variantPriceModel[0].size.toString()
                )
                putDouble(
                    FirebaseAnalytics.Param.PRICE,
                    viewModel.product.value!!.variantPriceModel[0].price
                )
            }
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar
    }

    private fun setupViewModel() {
        viewModel.apply {
            val data = intent.getParcelableExtra<ProductModel>(PRODUCT)
            product.value = data
            data?.let {
                cartItemModel.value = CartItemModel(
                    it.id,
                    it.name,
                    it.preview[0],
                    it.variantPriceModel[0].size.size ?: "",
                    it.variantPriceModel[0].color.color ?: "",
                    it.variantPriceModel[0].price,
                    1
                )
            }

            viewModel.getRelatedProducts.observe(this@DetailProductActivity, {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        productRecommendedList.value = it.data
                        setupViewItemListAnalytic(it.data)
                    }
                    is Resource.Error -> {
                    }
                }
            })
        }
    }

    private fun setupViewItemListAnalytic(data: List<ProductModel>?) {
        data?.forEach { productModel ->
            val getCategory = resources.getStringArray(R.array.category).toList()
            relatedProductAnalytic.add(
                Bundle().apply {
                    putString(
                        FirebaseAnalytics.Param.ITEM_ID, productModel.id
                    )
                    putString(
                        FirebaseAnalytics.Param.ITEM_NAME, productModel.name
                    )
                    putString(
                        FirebaseAnalytics.Param.ITEM_CATEGORY,
                        getCategory[productModel.category]
                    )
                    putString(
                        FirebaseAnalytics.Param.ITEM_VARIANT,
                        productModel.variantPriceModel[0].color.toString()
                    )
                    putString(
                        FirebaseAnalytics.Param.ITEM_BRAND, productModel.brand
                    )
                    putDouble(
                        FirebaseAnalytics.Param.PRICE,
                        productModel.variantPriceModel[0].price
                    )
                }
            )
        }

        val productListWithIndex = mutableListOf<Parcelable>()
        relatedProductAnalytic.forEachIndexed { index, bundle ->
            productListWithIndex.add(
                Bundle(bundle).apply {
                    putLong(FirebaseAnalytics.Param.INDEX, index.toLong())
                }
            )
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_LIST_ID, viewModel.product.value?.id.toString())
            putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, "Related products")
            putParcelableArray(
                FirebaseAnalytics.Param.ITEMS,
                productListWithIndex.toTypedArray()
            )
        })

    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = this@DetailProductActivity
            vm = viewModel

            onAddCart = onAddCart()
            onAddFavorite = onAddFavorite()

            vpProductDetail.adapter =
                viewModel.product.value?.let { getProductDetailVpAdapter(it.preview) }

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
                        firebaseAnalytics.logEvent(
                            FirebaseAnalytics.Event.SELECT_ITEM,
                            Bundle().apply {
                                putString(
                                    FirebaseAnalytics.Param.ITEM_LIST_ID,
                                    viewModel.product.value?.id.toString()
                                )
                                putString(
                                    FirebaseAnalytics.Param.ITEM_LIST_NAME,
                                    "Related products"
                                )
                                putParcelableArray(
                                    FirebaseAnalytics.Param.ITEMS,
                                    listOf<Parcelable>(relatedProductAnalytic[position]).toTypedArray()
                                )
                            })
                    },
                    onLongClickListener = { position ->

                    }
                )
            }

        }
    }

    private fun getProductDetailVpAdapter(images: List<String>): GeneralViewPagerAdapter {
        val listImage = mutableListOf<Fragment>()
        images.forEach { image ->
            listImage.add(PreviewFragment.newInstance(image))
        }

        return GeneralViewPagerAdapter(
            this, listImage
        )
    }

    private fun onAddCart() = ButtonClickListener {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, Bundle().apply {
            bundleProduct.apply {
                putLong(
                    FirebaseAnalytics.Param.QUANTITY,
                    1
                )
                putString(
                    FirebaseAnalytics.Param.CURRENCY,
                    "USD"
                )
                putDouble(
                    FirebaseAnalytics.Param.VALUE,
                    2 * 9.99
                )
                putString(
                    FirebaseAnalytics.Param.ITEM_VARIANT,
                    viewModel.product.value!!.variantPriceModel[0].size.toString()
                )
                putDouble(
                    FirebaseAnalytics.Param.PRICE,
                    viewModel.product.value!!.variantPriceModel[0].price
                )
            }
        })

        lifecycleScope.launch(Dispatchers.Main) {
            println("sessionManager.userId : ${sessionManager.userId}")
            viewModel.addCart(sessionManager.userId).observe(this@DetailProductActivity, {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        toast("Successfully added to cart ")
                    }
                    is Resource.Error -> {
                    }
                }
            })
        }
    }

    private fun onAddFavorite() = ButtonClickListener {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.setFavorite()
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, Bundle().apply {
            bundleProduct.apply {
                putString(
                    FirebaseAnalytics.Param.ITEM_VARIANT,
                    viewModel.product.value!!.variantPriceModel[0].size.toString()
                )
                putDouble(
                    FirebaseAnalytics.Param.PRICE,
                    viewModel.product.value!!.variantPriceModel[0].price
                )
            }
        })
    }
}