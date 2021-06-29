package com.opaku.id.core.domain.usecase

import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface AppUseCase {
    fun getProducts(): Flow<Resource<List<ProductModel>>>
}