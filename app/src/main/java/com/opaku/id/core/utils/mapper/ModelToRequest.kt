package com.opaku.id.core.utils.mapper

import com.opaku.id.core.data.source.remote.response.UserResponseItem
import com.opaku.id.core.domain.model.RegisterModel

object ModelToRequest {
    fun toRegisterRequest(model: RegisterModel): UserResponseItem {
        val request: UserResponseItem
        model.apply {
            request = UserResponseItem(
                id = id,
                username = username,
                email = email,
                password = password
            )
        }
        return request
    }
}