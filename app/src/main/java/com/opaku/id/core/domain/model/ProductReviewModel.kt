package com.opaku.id.core.domain.model


data class ProductReviewModel(
    val user: String,
    val rate: Int,
    val review: String,
    val images: List<String>
)