package com.opaku.id.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class ProductsResponseItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("category")
    val category: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("preview")
    val preview: List<String>,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("variant_price")
    val variantPrice: List<VariantPrice>,
    @SerializedName("variant")
    val variant: List<String>,
    @SerializedName("color")
    val color: List<String>,
    @SerializedName("reviews")
    val reviews: List<Review>
)