package com.opaku.id.ui.dashboard.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.opaku.id.R
import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.BannerModel
import com.opaku.id.core.domain.model.CategoryModel
import com.opaku.id.core.domain.model.FilterModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.ui.recyclerview.GeneralRecyclerViewAdapter
import com.opaku.id.core.ui.recyclerview.GridSpacingItemDecoration
import com.opaku.id.core.ui.recyclerview.HorizontalSpacingItemDecoration
import com.opaku.id.core.ui.viewpager.GeneralViewPagerAdapter
import com.opaku.id.core.utils.AnalyticCustomParam
import com.opaku.id.core.utils.Constant.FILTER
import com.opaku.id.core.utils.Constant.PRODUCT
import com.opaku.id.databinding.FragmentHomeBinding
import com.opaku.id.ui.dashboard.fragment.home.fragment.BannerFragment
import com.opaku.id.ui.detailproduct.DetailProductActivity
import com.opaku.id.ui.favoriteproduct.FavoriteProductActivity
import com.opaku.id.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setupBinding()
        setupViewModel()
        setupOnClickEvent()
        setupAnalytic()
    }

    private fun setupAnalytic() {
        firebaseAnalytics = Firebase.analytics
    }

    private fun setupOnClickEvent() {
        binding.ivFavorite.setOnClickListener {
            startActivity(Intent(requireActivity(), FavoriteProductActivity::class.java))
        }
    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            vpBanner.adapter = getBannerAdapter()

            rvCategory.apply {
                addItemDecoration(HorizontalSpacingItemDecoration(32))
                adapter = GeneralRecyclerViewAdapter<CategoryModel>(
                    layoutId = R.layout.item_home_category,
                    onClickListener = { model, position ->
                        val getCategory = resources.getStringArray(R.array.category).toList()

                        firebaseAnalytics.setUserProperty(
                            AnalyticCustomParam.SelectCategory,
                            getCategory[model.categoryId]
                        )

                        startActivity(
                            Intent(
                                requireActivity(),
                                SearchActivity::class.java
                            ).putExtra(FILTER, FilterModel(categoryId = model.categoryId))
                        )
                    },
                    onLongClickListener = { position ->

                    }
                )
            }

            rvFlashSale.apply {
                addItemDecoration(HorizontalSpacingItemDecoration(32))

                adapter = GeneralRecyclerViewAdapter<ProductModel>(
                    layoutId = R.layout.item_product,
                    onClickListener = { model, position ->
                        startActivity(
                            Intent(
                                requireActivity(),
                                DetailProductActivity::class.java
                            ).putExtra(PRODUCT, model)
                        )
                    },
                    onLongClickListener = { position ->

                    }
                )
            }

            rvMegaSale.apply {
                addItemDecoration(HorizontalSpacingItemDecoration(32))

                adapter = GeneralRecyclerViewAdapter<ProductModel>(
                    layoutId = R.layout.item_product,
                    onClickListener = { model, position ->
                        startActivity(
                            Intent(
                                requireActivity(),
                                DetailProductActivity::class.java
                            ).putExtra(PRODUCT, model)
                        )
                    },
                    onLongClickListener = { position ->

                    }
                )
            }

            rvRecommendedProduct.apply {
                addItemDecoration(GridSpacingItemDecoration(2, 32, false))
                isNestedScrollingEnabled = false
                adapter = GeneralRecyclerViewAdapter<ProductModel>(
                    layoutId = R.layout.item_product_grid,
                    onClickListener = { model, position ->
                        startActivity(
                            Intent(
                                requireActivity(),
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
        viewModel.categoryList.value = getCategory()
        viewModel.getProducts.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    viewModel.recommendedProductList.value = it.data
                    viewModel.flashSaleProductList.value = it.data
                    viewModel.recommendedProductList.value = it.data
                }
                is Resource.Error -> {
                }
            }
        })
    }

    private fun getCategory(): List<CategoryModel> {
        return listOf(
            CategoryModel(
                8,
                R.drawable.ic_baby_boy_clothes_with_anchor,
                "Boy Cloth"
            ),
            CategoryModel(
                9,
                R.drawable.ic_baby_boy_shoes,
                "Boy Shoes"
            ),
            CategoryModel(
                11,
                R.drawable.ic_baby_boy_sock,
                "Boy Sock"
            ),
            CategoryModel(
                5,
                R.drawable.ic_toy_horse,
                "Toy"
            ),
            CategoryModel(
                12,
                R.drawable.ic_girl_clothes,
                "Girl Cloth"
            ),
            CategoryModel(
                15,
                R.drawable.ic_baby_girl_sock,
                "Girl Sock"
            )
        )
    }

    private fun getBannerAdapter() = GeneralViewPagerAdapter(
        requireActivity(), listOf(
            BannerFragment.newInstance(
                BannerModel(
                    "promotion_image_1.png",
                    "Super Flash Sale\n50% Off",
                    86400000
                )
            ),
            BannerFragment.newInstance(
                BannerModel(
                    "promotion_image_2.png",
                    "Super Flash Sale\n50% Off",
                    86400000
                )
            )
        )
    )

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}