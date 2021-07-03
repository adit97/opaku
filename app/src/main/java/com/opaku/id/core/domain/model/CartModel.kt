package com.opaku.id.core.domain.model

data class CartModel(
    val productId: String,
    val totalItem: Int,
    var product: ProductModel? = null
)