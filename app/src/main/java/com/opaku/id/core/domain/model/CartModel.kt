package com.opaku.id.core.domain.model

data class CartModel(
    val user: Long,
    val cart: List<CartItemModel>
)