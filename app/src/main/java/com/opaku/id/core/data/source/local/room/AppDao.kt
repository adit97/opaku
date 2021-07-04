package com.opaku.id.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.opaku.id.core.data.source.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteProduct(entity: FavoriteProductEntity)

    @Query("DELETE FROM favorite_product WHERE product_id = :productId")
    fun deleteFavoriteProduct(productId: String)

    @Query("SELECT * FROM favorite_product")
    fun favoriteProducts(): Flow<List<FavoriteProductEntity>>

    @Query("SELECT product_id FROM favorite_product WHERE product_id = :productId")
    fun isFavoriteProduct(productId: String): Flow<String>

}