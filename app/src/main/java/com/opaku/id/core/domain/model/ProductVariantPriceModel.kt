package com.opaku.id.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductVariantPriceModel(
    val size: ProductVariantModel,
    val color: ProductColorModel,
    val image: String,
    val price: Double,
    val originalPrice: Double,
    val discount: Int
) : Parcelable