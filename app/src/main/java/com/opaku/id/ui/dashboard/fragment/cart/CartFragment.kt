package com.opaku.id.ui.dashboard.fragment.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.opaku.id.core.data.Resource
import com.opaku.id.core.ui.recyclerview.VerticalSpacingItemDecoration
import com.opaku.id.core.utils.toDp
import com.opaku.id.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(layoutInflater, container, false)
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

    private fun setupViewModel() {
        viewModel.getProduct.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    viewModel.carts.observe(viewLifecycleOwner, { listCart ->

//                        val mList = mutableListOf<String>()
//                        it.data?.forEach { pm ->
//                            mList.add(pm.id)
//                        }
//
//                        println("mList : $mList")
//
//                        println("listCart : $listCart")
//
//                        if (listCart.isNotEmpty()) {
//                            val filter = listCart.flatMap { pm ->
//                                it.data!!.filter { cm ->
//                                    pm.productId == cm.id
//                                }
//                            }
//                            filter.forEach {filte->
//                                println("filter : ${filte.id}")
//                            }
//                        }


                        listCart.forEach { cm ->
                            it.data?.forEach forPm@{ pm ->
                                if (cm.productId == pm.id) {
                                    cm.product = pm
                                    return@forPm
                                }
                            }
                        }

                        viewModel.cartList.value = listCart
                    })

                }
                is Resource.Error -> {
                }
            }
        })
    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            rvCart.apply {
                addItemDecoration(VerticalSpacingItemDecoration(requireContext().toDp(16)))
                adapter = CartRecyclerViewAdapter(
                    onClickListener = { model, position ->
                        println("onClickListener")
                    },
                    onAdd = { model ->
                        println("onAdd")
                    },
                    onSubtract = { model ->
                        println("onSubtract")
                    },
                    onDelete = { model ->
                        println("onDelete")
                    }
                )
            }
        }
    }

    companion object {
        fun newInstance() = CartFragment()
    }
}