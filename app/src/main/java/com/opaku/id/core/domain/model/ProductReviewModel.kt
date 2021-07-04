package com.opaku.id.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductReviewModel(
    val user: String,
    val rate: Float,
    val review: String,
    val date: String,
    val images: List<String>
) : Parcelable