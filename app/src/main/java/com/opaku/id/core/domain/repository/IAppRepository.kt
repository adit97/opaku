package com.opaku.id.core.domain.repository

import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface IAppRepository {
    fun getProducts(): Flow<Resource<List<ProductModel>>>
}