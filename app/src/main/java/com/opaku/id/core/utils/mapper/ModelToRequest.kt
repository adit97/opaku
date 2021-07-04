package com.opaku.id.core.utils.mapper

import com.opaku.id.core.data.source.remote.response.Cart
import com.opaku.id.core.data.source.remote.response.CartResponseItem
import com.opaku.id.core.data.source.remote.response.UserResponseItem
import com.opaku.id.core.domain.model.CartItemModel
import com.opaku.id.core.domain.model.CartModel
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

    fun toCartRequest(model: CartModel): CartResponseItem {
        val request: CartResponseItem
        model.apply {
            request = CartResponseItem(
                user = user,
                cart = toCartItem(cart)
            )
        }
        return request
    }

    private fun toCartItem(model: List<CartItemModel>): List<Cart> {
        val request = mutableListOf<Cart>()
        model.forEach {
            it.apply {
                request.add(
                    Cart(
                        id = id,
                        name = name,
                        image = image,
                        variant = variant,
                        color = color,
                        price = price,
                        quantity = quantity
                    )
                )
            }
        }
        return request
    }
}