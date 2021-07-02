package com.opaku.id.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_product")
data class FavoriteProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: String
)