package com.opaku.id.core.domain.repository

import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface IAppRepository {
    fun getProducts(): Flow<Resource<List<ProductModel>>>
    suspend fun addFavoriteProduct(productId: String)
    fun favoriteProducts(): Flow<List<String>>
    fun deleteFavoriteProduct(productId: String)
    fun isFavoriteProduct(productId: String): Flow<String>
    suspend fun addChart(model: CartModel)
    fun carts(): Flow<List<CartModel>>
    fun deleteCart(productId: String)
    fun login(model: UserModel): Flow<Resource<Long>>
    fun register(model: RegisterModel): Flow<Resource<Boolean>>
    fun filterProduct(model: FilterModel): Flow<Resource<List<ProductModel>>>
}