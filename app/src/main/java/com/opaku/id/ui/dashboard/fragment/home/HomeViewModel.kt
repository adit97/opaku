package com.opaku.id.ui.dashboard.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.opaku.id.core.domain.model.CategoryModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(appUseCase: AppUseCase): ViewModel() {
    val categoryList = MutableLiveData<List<CategoryModel>>()
    val flashSaleProductList = MutableLiveData<List<ProductModel>>()
    val recommendedProductList = MutableLiveData<List<ProductModel>>()

    val getProducts = appUseCase.getProducts().asLiveData()
}