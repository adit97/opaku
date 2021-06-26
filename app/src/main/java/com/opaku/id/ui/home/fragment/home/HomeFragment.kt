package com.opaku.id.ui.home.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.opaku.id.R
import com.opaku.id.core.domain.model.CategoryModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.ui.recyclerview.GeneralRecyclerViewAdapter
import com.opaku.id.core.ui.recyclerview.GridSpacingItemDecoration
import com.opaku.id.core.ui.recyclerview.HorizontalSpacingItemDecoration
import com.opaku.id.core.ui.viewpager.GeneralViewPagerAdapter
import com.opaku.id.databinding.FragmentHomeBinding
import com.opaku.id.ui.home.fragment.home.fragment.BannerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

                    },
                    onLongClickListener = { position ->

                    }
                )
            }

        }
    }

    private fun setupViewModel() {
        viewModel.categoryList.value = getCategory()
        viewModel.flashSaleProductList.value = getFlashSaleProduct()
        viewModel.recommendedProductList.value = getRecommendedSaleProduct()
    }

    private fun getFlashSaleProduct(): List<ProductModel> =
        listOf(
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            )
        )

    private fun getRecommendedSaleProduct(): List<ProductModel> =
        listOf(
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            ),
            ProductModel(
                1,
                R.drawable.shoes_1,
                "FS - Nike Air Max 270 React",
                200000,
                20
            )
        )

    private fun getCategory(): List<CategoryModel> {
        return listOf(
            CategoryModel(
                R.drawable.ic_tshirt,
                "Man Shirt"
            ),
            CategoryModel(
                R.drawable.ic_dress,
                "Dress"
            ),
            CategoryModel(
                R.drawable.ic_man_bag,
                "Man Work Equipment"
            ),
            CategoryModel(
                R.drawable.ic_woman_bag,
                "Woman Bag"
            ),
            CategoryModel(
                R.drawable.ic_man_shoes,
                "Man Shoes"
            ),
            CategoryModel(
                R.drawable.ic_woman_shoes,
                "Height Heels"
            )
        )
    }

    private fun getBannerAdapter() = GeneralViewPagerAdapter(
        requireActivity(), listOf(
            BannerFragment.newInstance(R.drawable.promotion_image_1),
            BannerFragment.newInstance(R.drawable.promotion_image_2),
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