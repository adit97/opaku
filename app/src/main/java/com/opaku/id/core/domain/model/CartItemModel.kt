package com.opaku.id.core.domain.model

data class CartItemModel(
    val id: String,
    val name: String,
    val image: String,
    var variant: String,
    var color: String,
    val price: Double,
    val quantity: Int
)