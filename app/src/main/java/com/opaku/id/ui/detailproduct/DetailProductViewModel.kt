package com.opaku.id.ui.detailproduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opaku.id.core.domain.model.ProductColorModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.model.ProductVariantModel
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailProductViewModel @Inject constructor(appUseCase: AppUseCase) : ViewModel() {
    val productSizeList = MutableLiveData<List<ProductVariantModel>>()
    val productColorList = MutableLiveData<List<ProductColorModel>>()
    val productRecommendedList = MutableLiveData<List<ProductModel>>()

    fun setProductSizeSelected(position: Int) {
        val tempData = productSizeList.value

        if (!tempData.isNullOrEmpty()) {
            tempData.forEach {
                it.isSelected = false
            }

            tempData[position].isSelected = true
            productSizeList.value = tempData!!
        }
    }

    fun setProductColorSelected(position: Int) {
        val tempData = productColorList.value

        if (!tempData.isNullOrEmpty()) {
            tempData.forEach {
                it.isSelected = false
            }

            tempData[position].isSelected = true
            productColorList.value = tempData!!
        }
    }


}