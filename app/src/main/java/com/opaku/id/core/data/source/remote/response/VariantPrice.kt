package com.opaku.id.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class VariantPrice(
    @SerializedName("size")
    val size: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("original_price")
    val originalPrice: Double,
    @SerializedName("discount")
    val discount: Int
)