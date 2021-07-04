package com.opaku.id.ui.detailproduct

import androidx.lifecycle.*
import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.CartItemModel
import com.opaku.id.core.domain.model.CartModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailProductViewModel @Inject constructor(val appUseCase: AppUseCase) : ViewModel() {
    val product = MutableLiveData<ProductModel>()
    val cartItemModel = MutableLiveData<CartItemModel>()
    val productRecommendedList = MutableLiveData<List<ProductModel>>()

    val getRelatedProducts = appUseCase.getProducts().asLiveData()

    fun setProductSizeSelected(position: Int) {
        val tempData = product.value

        if (tempData != null) {
            tempData.variant.forEach {
                it.isSelected = false
            }

            tempData.variant[position].isSelected = true
            cartItemModel.value!!.variant = tempData.variant[position].size.toString()
            product.value = tempData!!
        }
    }

    fun setProductColorSelected(position: Int) {
        val tempData = product.value

        if (tempData != null) {
            tempData.color.forEach {
                it.isSelected = false
            }

            tempData.color[position].isSelected = true
            cartItemModel.value!!.color = tempData.color[position].color.toString()
            product.value = tempData!!
        }
    }

    suspend fun setFavorite() {
        if (isFavorite.value == null) {
            product.value?.let { appUseCase.addFavoriteProduct(it.id) }
        } else {
            product.value?.let { appUseCase.deleteFavoriteProduct(it.id) }
        }
    }

    val isFavorite = product.switchMap {
        appUseCase.isFavoriteProduct(it.id).asLiveData()
    }

    fun addCart(userId: Long): LiveData<Resource<Boolean>> {
        return appUseCase.addCart(
            CartModel(
                user = userId,
                listOf(
                    cartItemModel.value!!
                )
            )
        ).asLiveData()
    }
}