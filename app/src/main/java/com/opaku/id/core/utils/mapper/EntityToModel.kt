package com.opaku.id.core.utils.mapper

import com.opaku.id.core.data.source.local.entity.CartProductEntity
import com.opaku.id.core.data.source.local.entity.FavoriteProductEntity
import com.opaku.id.core.domain.model.CartModel

object EntityToModel {
    fun toFavoriteProducts(entity: List<FavoriteProductEntity>): List<String> {
        val list = mutableListOf<String>()
        entity.forEach {
            list.add(it.productId)
        }

        return list
    }

    fun toCartModel(entity: List<CartProductEntity>): List<CartModel> {
        val list = mutableListOf<CartModel>()
        entity.forEach {
            it.apply {
                list.add(
                    CartModel(
                        productId = productId,
                        totalItem = totalItem
                    )
                )
            }
        }

        return list
    }
}
