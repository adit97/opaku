package com.opaku.id.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class UserResponseItem(
    @SerializedName("id")
    val id: Long,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)