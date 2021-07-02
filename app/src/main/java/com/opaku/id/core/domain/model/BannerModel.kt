package com.opaku.id.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BannerModel(
    val image: String,
    val title: String,
    val time: Long
) : Parcelable