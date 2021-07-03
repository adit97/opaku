package com.opaku.id.core.domain.usecase

import com.opaku.id.core.data.Resource
import com.opaku.id.core.data.source.local.entity.CartProductEntity
import com.opaku.id.core.domain.model.CartModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.model.RegisterModel
import com.opaku.id.core.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface AppUseCase {
    fun getProducts(): Flow<Resource<List<ProductModel>>>
    suspend fun addFavoriteProduct(productId: String)
    fun favoriteProducts(): Flow<List<String>>
    fun deleteFavoriteProduct(productId: String)
    fun isFavoriteProduct(productId: String): Flow<String>
    suspend fun addChart(model: CartModel)
    fun carts(): Flow<List<CartModel>>
    fun deleteCart(productId: String)
    fun login(model: UserModel): Flow<Resource<Boolean>>
    fun register(model: RegisterModel): Flow<Resource<Boolean>>
}