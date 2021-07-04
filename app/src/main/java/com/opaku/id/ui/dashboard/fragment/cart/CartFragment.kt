package com.opaku.id.ui.dashboard.fragment.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.opaku.id.R
import com.opaku.id.core.data.Resource
import com.opaku.id.core.data.source.local.session.ISessionManager
import com.opaku.id.core.domain.model.CartModel
import com.opaku.id.core.ui.recyclerview.VerticalSpacingItemDecoration
import com.opaku.id.core.utils.toDp
import com.opaku.id.core.utils.toast
import com.opaku.id.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {

    @Inject
    lateinit var sessionManager: ISessionManager

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private lateinit var firebaseAnalytics: FirebaseAnalytics

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
        setupViewModel()
        setupBinding()
        setupAnalytic()
    }

    private fun setupAnalytic() {
        firebaseAnalytics = Firebase.analytics
    }

    private fun setupViewModel() {
        viewModel.userId.value = sessionManager.userId
        viewModel.carts.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    viewModel.cartList.value = it.data?.cart

                    var totalPrice = 0.0
                    val bundleList = mutableListOf<Bundle>()
                    it.data?.cart?.forEach { ct ->
                        bundleList.add(
                            Bundle().apply {
                                putString(FirebaseAnalytics.Param.ITEM_ID, ct.id)
                                putString(FirebaseAnalytics.Param.ITEM_NAME, ct.name)
                                putString(
                                    FirebaseAnalytics.Param.ITEM_VARIANT,
                                    "${ct.variant} - ${ct.color}"
                                )
                                putDouble(FirebaseAnalytics.Param.PRICE, ct.price)
                                putLong(FirebaseAnalytics.Param.QUANTITY, ct.quantity.toLong())
                            }
                        )

                        totalPrice += ct.quantity.toDouble() * ct.price
                    }

                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_CART, Bundle().apply {
                        putString(FirebaseAnalytics.Param.CURRENCY, "USD")
                        putDouble(FirebaseAnalytics.Param.VALUE, totalPrice)
                        putParcelableArray(FirebaseAnalytics.Param.ITEMS, bundleList.toTypedArray())
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
                    onClickListener = { model, position -> },
                    onAdd = { model ->
                        println("onAdd")
                    },
                    onSubtract = { model ->
                        println("onSubtract")
                    },
                    onDelete = { model ->
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage(context.getString(R.string.cart_delete_product_message))
                            .setPositiveButton(
                                context.getString(R.string.general_delete)
                            ) { dialog, id ->
                                val productToRemoveBundle = Bundle().apply {
                                    putString(FirebaseAnalytics.Param.ITEM_ID, model.id)
                                    putString(FirebaseAnalytics.Param.ITEM_NAME, model.name)
                                    putString(
                                        FirebaseAnalytics.Param.ITEM_VARIANT,
                                        "${model.variant} - ${model.color}"
                                    )
                                    putDouble(FirebaseAnalytics.Param.PRICE, model.price)
                                    putLong(
                                        FirebaseAnalytics.Param.QUANTITY,
                                        model.quantity.toLong()
                                    )
                                }

                                firebaseAnalytics.logEvent(
                                    FirebaseAnalytics.Event.REMOVE_FROM_CART,
                                    Bundle().apply {
                                        putString(FirebaseAnalytics.Param.CURRENCY, "USD")
                                        putDouble(
                                            FirebaseAnalytics.Param.VALUE,
                                            model.quantity.toDouble() * model.price
                                        )
                                        putParcelableArray(
                                            FirebaseAnalytics.Param.ITEMS,
                                            listOf(productToRemoveBundle).toTypedArray()
                                        )
                                    })

                                viewModel.removeCart(
                                    CartModel(
                                        user = sessionManager.userId,
                                        cart = listOf(model)
                                    )
                                ).observe(viewLifecycleOwner, {
                                    when (it) {
                                        is Resource.Loading -> {
                                        }
                                        is Resource.Success -> {
                                            requireContext().toast(context.getString(R.string.cart_message_successfully_deleted_item))
                                            viewModel.userId.value = sessionManager.userId
                                        }
                                        is Resource.Error -> {
                                        }
                                    }
                                })
                            }
                            .setNegativeButton(
                                context.getString(R.string.general_cancel)
                            ) { dialog, id ->
                                dialog.dismiss()
                            }
                        builder.create().show()
                    }
                )
            }
        }
    }

    companion object {
        fun newInstance() = CartFragment()
    }
}