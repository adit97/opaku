package com.opaku.id.core.utils.mapper

import com.opaku.id.core.data.source.local.entity.FavoriteProductEntity

object EntityToModel {
    fun toFavoriteProducts(entity: List<FavoriteProductEntity>): List<String> {
        val list = mutableListOf<String>()
        entity.forEach {
            list.add(it.productId)
        }

        return list
    }
}
