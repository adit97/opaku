package com.opaku.id.core.domain.repository

import com.opaku.id.core.data.Resource
import com.opaku.id.core.data.source.local.entity.FavoriteProductEntity
import com.opaku.id.core.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface IAppRepository {
    fun getProducts(): Flow<Resource<List<ProductModel>>>
    suspend fun addFavoriteProduct(productId: String)
    fun favoriteProducts(): Flow<List<String>>
    fun deleteFavoriteProduct(productId: String)
    fun isFavoriteProduct(productId: String): Flow<String>
}