package com.opaku.id.core.data.source.remote

import android.content.Context
import com.google.gson.Gson
import com.opaku.id.core.data.source.remote.network.ApiResponse
import com.opaku.id.core.data.source.remote.network.ApiService
import com.opaku.id.core.data.source.remote.response.ProductsResponse
import com.opaku.id.core.utils.parsingFileToString
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

}