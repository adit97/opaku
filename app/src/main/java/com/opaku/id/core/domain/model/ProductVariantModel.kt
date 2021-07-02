package com.opaku.id.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductVariantModel(
    val size: String?,
    var isSelected: Boolean = false
) : Parcelable