package com.opaku.id.core.utils.mapper

import com.opaku.id.core.data.source.local.entity.CartProductEntity
import com.opaku.id.core.domain.model.CartModel

object ModelToEntity {
    fun toCartEntity(model: CartModel): CartProductEntity {
        val entity: CartProductEntity
        model.apply {
            entity = CartProductEntity(
                productId = productId,
                totalItem = totalItem
            )
        }

        return entity
    }
}