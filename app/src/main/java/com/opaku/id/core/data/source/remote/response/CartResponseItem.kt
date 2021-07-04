package com.opaku.id.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class CartResponseItem(
    @SerializedName("user")
    val user: Long,
    @SerializedName("cart")
    var cart: List<Cart>
)