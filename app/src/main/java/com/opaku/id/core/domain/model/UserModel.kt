package com.opaku.id.core.domain.model


data class UserModel(
    var id: Long = 0,
    val username: String = "",
    var email: String = "",
    var password: String = ""
)