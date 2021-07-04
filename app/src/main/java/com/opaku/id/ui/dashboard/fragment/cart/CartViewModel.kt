package com.opaku.id.ui.dashboard.fragment.cart

import androidx.lifecycle.*
import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.CartItemModel
import com.opaku.id.core.domain.model.CartModel
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    val userId = MutableLiveData<Long>()
    val cartList = MutableLiveData<List<CartItemModel>>()

    val carts = userId.switchMap {
        appUseCase.carts(it).asLiveData()
    }

    fun removeCart(model: CartModel): LiveData<Resource<Boolean>> {
        return appUseCase.removeCart(model).asLiveData()
    }

}