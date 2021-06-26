package com.opaku.id.core.domain.model

data class ProductModel(
    val productId: Int,
    val image: Int,
    val name: String,
    val price: Int,
    val discount: Int
)