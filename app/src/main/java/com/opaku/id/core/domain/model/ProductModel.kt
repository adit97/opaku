package com.opaku.id.core.domain.model


data class ProductModel(
    val id: String,
    val name: String,
    val rate: Double,
    val category: Int,
    val weight: Int,
    val preview: List<String>,
    val brand: String,
    val desc: String,
    val variantPriceModel: List<ProductVariantPriceModel>,
    val variant: List<ProductVariantModel>,
    val color: List<ProductColorModel>,
    val reviewModel: List<ProductReviewModel>,
    var isFavorite: Boolean = false
)