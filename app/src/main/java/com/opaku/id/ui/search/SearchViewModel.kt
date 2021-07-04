package com.opaku.id.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.opaku.id.core.domain.model.FilterModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(appUseCase: AppUseCase) : ViewModel() {
    val filterModel = MutableLiveData<FilterModel>()
    val productAfterFilter = MutableLiveData<List<ProductModel>>()

    val filterProduct = filterModel.switchMap {
        appUseCase.filterProduct(it).asLiveData()
    }
}