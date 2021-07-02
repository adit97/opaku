package com.opaku.id.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val id: String,
    val name: String,
    val rate: Float,
    val category: Int,
    val weight: Int,
    val preview: List<String>,
    val brand: String,
    val desc: String,
    val variantPriceModel: List<ProductVariantPriceModel>,
    var variant: List<ProductVariantModel>,
    var color: List<ProductColorModel>,
    val reviewModel: List<ProductReviewModel>,
    var isFavorite: Boolean = false
): Parcelable