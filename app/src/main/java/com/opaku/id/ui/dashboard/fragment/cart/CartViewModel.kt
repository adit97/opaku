package com.opaku.id.ui.dashboard.fragment.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.opaku.id.core.domain.model.CartModel
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    val cartList = MutableLiveData<List<CartModel>>()

    val carts = appUseCase.carts().asLiveData()

    fun deleteProduct(productId: String) {
        appUseCase.deleteCart(productId)
    }

    suspend fun updateProduct(model: CartModel) {
        appUseCase.addChart(model)
    }

    val getProduct = appUseCase.getProducts().asLiveData()

}