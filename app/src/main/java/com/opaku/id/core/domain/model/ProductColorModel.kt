package com.opaku.id.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductColorModel(
    val color: String?,
    var isSelected: Boolean = false
) : Parcelable