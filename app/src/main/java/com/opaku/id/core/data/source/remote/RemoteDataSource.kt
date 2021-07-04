package com.opaku.id.core.data.source.remote

import android.content.Context
import com.google.gson.Gson
import com.opaku.id.core.data.source.remote.network.ApiResponse
import com.opaku.id.core.data.source.remote.network.ApiService
import com.opaku.id.core.data.source.remote.response.*
import com.opaku.id.core.domain.model.FilterModel
import com.opaku.id.core.domain.model.UserModel
import com.opaku.id.core.utils.parsingFileToString
import com.opaku.id.core.utils.parsingFileToStringFromInternalStorage
import com.opaku.id.core.utils.writeFileToInternalStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService
) {

    fun getProducts() = flow {
        try {
            val jsonString = context.parsingFileToString("products.json")
            val convertToObject = Gson().fromJson(jsonString, ProductsResponse::class.java)
            emit(ApiResponse.Success(convertToObject))
        } catch (ex: IOException) {
            emit(ApiResponse.Error(ex.message.toString()))
        }
    }

    fun login(model: UserModel) = flow {
        try {
            val jsonString = context.parsingFileToStringFromInternalStorage("user.json")
            val convertToObject = Gson().fromJson(jsonString, UserResponse::class.java)

            val searchUser = convertToObject.filter { ur ->
                ur.email == model.email && ur.password == model.password
            }

            if (searchUser.isNullOrEmpty()) {
                emit(ApiResponse.Success(0L))
            } else {
                emit(ApiResponse.Success(searchUser[0].id))
            }
        } catch (ex: IOException) {
            emit(ApiResponse.Error(ex.message.toString()))
        }
    }

    fun register(request: UserResponseItem) = flow {
        try {
            val jsonString = context.parsingFileToStringFromInternalStorage("user.json")
            val convertToObject = Gson().fromJson(jsonString, UserResponse::class.java)
            convertToObject.add(request)

            context.writeFileToInternalStorage(Gson().toJson(convertToObject), "user.json")
            emit(ApiResponse.Success(true))
        } catch (ex: IOException) {
            emit(ApiResponse.Error(ex.message.toString()))
        }
    }

    fun filterProduct(model: FilterModel) = flow {
        try {
            val jsonString = context.parsingFileToString("products.json")
            val convertToObject = Gson().fromJson(jsonString, ProductsResponse::class.java)

            if (model.categoryId == 0) {
                emit(ApiResponse.Success(convertToObject))
            } else {
                val dataAfterFilter = convertToObject.filter {
                    it.category == model.categoryId
                }
                emit(ApiResponse.Success(dataAfterFilter))
            }

        } catch (ex: IOException) {
            emit(ApiResponse.Error(ex.message.toString()))
        }
    }

    fun addCart(cartResponseItem: CartResponseItem) = flow {
        try {
            val jsonString = context.parsingFileToStringFromInternalStorage("cart.json")
            val convertToObject = if (jsonString == "[{}]") {
                CartResponse()
            } else {
                Gson().fromJson(jsonString, CartResponse::class.java)
            }

            if (convertToObject.isNullOrEmpty()) {
                convertToObject.add(cartResponseItem)
            } else {
                var found = false
                convertToObject.forEach searchUser@{ ci ->
                    if (ci.user == cartResponseItem.user) {

                        cartResponseItem.cart.forEach searchProductModel@{ cm ->
                            var cmFound = false

                            ci.cart.forEach searchProductJson@{ cs ->
                                if (cs.id == cm.id && cs.color == cm.color && cs.variant == cm.variant) {
                                    cmFound = true
                                    cs.quantity += 1
                                    return@searchProductModel
                                }
                            }

                            if (!cmFound) {
                                val temp = mutableListOf<Cart>()
                                temp.addAll(ci.cart)
                                temp.add(cm)
                                ci.cart = temp
                            }
                        }
                        found = true
                        return@searchUser
                    }
                }

                if (!found) {
                    convertToObject.add(cartResponseItem)
                }
            }

            context.writeFileToInternalStorage(Gson().toJson(convertToObject), "cart.json")
            emit(ApiResponse.Success(true))
        } catch (ex: IOException) {
            emit(ApiResponse.Error(ex.message.toString()))
        }
    }

    fun carts(userId: Long) = flow {
        try {
            val jsonString = context.parsingFileToStringFromInternalStorage("cart.json")
            val convertToObject = Gson().fromJson(jsonString, CartResponse::class.java)
            val filter = convertToObject.filter {
                it.user == userId
            }
            if (filter.isNullOrEmpty()) {
                emit(ApiResponse.Success(null))
            } else {
                emit(ApiResponse.Success(filter[0]))
            }
        } catch (ex: IOException) {
            emit(ApiResponse.Error(ex.message.toString()))
        }
    }

    fun removeCart(cartResponseItem: CartResponseItem) = flow {
        try {
            val jsonString = context.parsingFileToStringFromInternalStorage("cart.json")
            val convertToObject = Gson().fromJson(jsonString, CartResponse::class.java)


            convertToObject.forEach searchUser@{ ci ->
                if (ci.user == cartResponseItem.user) {

                    cartResponseItem.cart.forEach searchProductModel@{ cm ->
                        ci.cart.forEach searchProductJson@{ cs ->
                            if (cs.id == cm.id && cs.color == cm.color && cs.variant == cm.variant) {

                                val temp = mutableListOf<Cart>()
                                temp.addAll(ci.cart)
                                temp.remove(cm)
                                ci.cart = temp

                                return@searchProductModel
                            }
                        }
                    }
                    return@searchUser
                }
            }

            context.writeFileToInternalStorage(Gson().toJson(convertToObject), "cart.json")
            emit(ApiResponse.Success(true))
        } catch (ex: IOException) {
            emit(ApiResponse.Error(ex.message.toString()))
        }
    }
}