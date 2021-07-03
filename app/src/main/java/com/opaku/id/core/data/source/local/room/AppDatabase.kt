package com.opaku.id.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.opaku.id.core.data.source.local.entity.CartProductEntity
import com.opaku.id.core.data.source.local.entity.FavoriteProductEntity
import com.opaku.id.core.data.source.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, FavoriteProductEntity::class, CartProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}
