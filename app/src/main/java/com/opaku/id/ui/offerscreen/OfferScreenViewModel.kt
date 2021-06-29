package com.opaku.id.ui.offerscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfferScreenViewModel @Inject constructor(appUseCase: AppUseCase): ViewModel() {
    val promotionProductList = MutableLiveData<List<ProductModel>>()
}