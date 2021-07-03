package com.opaku.id.core.domain.model


data class RegisterModel(
    var id: Long = 0,
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var passwordAgain: String = "",
)