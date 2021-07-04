package com.opaku.id.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("user")
    val user: String,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("review")
    val review: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("images")
    val images: List<String>
)