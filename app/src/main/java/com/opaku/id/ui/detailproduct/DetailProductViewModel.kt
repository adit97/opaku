package com.opaku.id.ui.detailproduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailProductViewModel @Inject constructor(val appUseCase: AppUseCase) : ViewModel() {
    val product = MutableLiveData<ProductModel>()
    val productRecommendedList = MutableLiveData<List<ProductModel>>()

    val getRelatedProducts = appUseCase.getProducts().asLiveData()

    fun setProductSizeSelected(position: Int) {
        val tempData = product.value

        if (tempData != null) {
            tempData.variant.forEach {
                it.isSelected = false
            }

            tempData.variant[position].isSelected = true
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
}