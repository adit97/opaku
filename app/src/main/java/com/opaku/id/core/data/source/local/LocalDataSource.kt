package com.opaku.id.core.data.source.local

import com.opaku.id.core.data.source.local.entity.FavoriteProductEntity
import com.opaku.id.core.data.source.local.room.AppDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val appDao: AppDao) {

    suspend fun addFavoriteProduct(entity: FavoriteProductEntity) = appDao.addFavoriteProduct(entity)

    fun favoriteProducts() = appDao.favoriteProducts()

    fun deleteFavoriteProduct(productId: String) = appDao.deleteFavoriteProduct(productId)

    fun isFavoriteProduct(productId: String) = appDao.isFavoriteProduct(productId)
}