package com.opaku.id.core.domain.model


data class ProductVariantPriceModel(
    val size: ProductVariantModel,
    val color: ProductColorModel,
    val image: String,
    val price: Double,
    val originalPrice: Double,
    val discount: Int
)