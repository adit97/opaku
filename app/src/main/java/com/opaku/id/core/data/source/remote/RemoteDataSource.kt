package com.opaku.id.core.data.source.remote

import android.content.Context
import com.google.gson.Gson
import com.opaku.id.core.data.source.remote.network.ApiResponse
import com.opaku.id.core.data.source.remote.network.ApiService
import com.opaku.id.core.data.source.remote.response.ProductsResponse
import com.opaku.id.core.data.source.remote.response.UserResponse
import com.opaku.id.core.data.source.remote.response.UserResponseItem
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
}