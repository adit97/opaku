package com.opaku.id.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("variant")
    val variant: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("quantity")
    var quantity: Int
)