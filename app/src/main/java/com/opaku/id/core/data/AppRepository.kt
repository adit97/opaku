package com.opaku.id.core.data

import com.opaku.id.core.data.source.local.LocalDataSource
import com.opaku.id.core.data.source.local.entity.FavoriteProductEntity
import com.opaku.id.core.data.source.remote.RemoteDataSource
import com.opaku.id.core.data.source.remote.network.ApiResponse
import com.opaku.id.core.data.source.remote.response.ProductsResponse
import com.opaku.id.core.data.source.remote.response.ProductsResponseItem
import com.opaku.id.core.domain.model.*
import com.opaku.id.core.domain.repository.IAppRepository
import com.opaku.id.core.utils.AppExecutors
import com.opaku.id.core.utils.mapper.EntityToModel
import com.opaku.id.core.utils.mapper.ModelToEntity
import com.opaku.id.core.utils.mapper.ModelToRequest
import com.opaku.id.core.utils.mapper.ResponseToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IAppRepository {
    override fun getProducts(): Flow<Resource<List<ProductModel>>> =
        object : NetworkBoundResource<List<ProductModel>, ProductsResponse>() {
            override fun returnResult(data: ProductsResponse): Flow<List<ProductModel>> = flow {
                emit(ResponseToModel.toProductsModelList(data))
            }

            override suspend fun createCall(): Flow<ApiResponse<ProductsResponse>> =
                remoteDataSource.getProducts()
        }.asFlow()

    override suspend fun addFavoriteProduct(productId: String) {
        localDataSource.addFavoriteProduct(FavoriteProductEntity(productId))
    }

    override fun favoriteProducts(): Flow<List<String>> =
        localDataSource.favoriteProducts().map {
            EntityToModel.toFavoriteProducts(it)
        }

    override fun deleteFavoriteProduct(productId: String) {
        appExecutors.diskIO().execute {
            localDataSource.deleteFavoriteProduct(productId)
        }
    }

    override fun isFavoriteProduct(productId: String): Flow<String> =
        localDataSource.isFavoriteProduct(productId)

    override suspend fun addChart(model: CartModel) {
        localDataSource.addChart(ModelToEntity.toCartEntity(model))
    }

    override fun carts(): Flow<List<CartModel>> = localDataSource.carts().map {
        EntityToModel.toCartModel(it)
    }

    override fun deleteCart(productId: String) {
        appExecutors.diskIO().execute {
            localDataSource.deleteCart(productId)
        }
    }

    override fun login(model: UserModel): Flow<Resource<Long>> =
        object : NetworkBoundResource<Long, Long>() {
            override fun returnResult(data: Long): Flow<Long> = flow {
                emit(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<Long>> =
                remoteDataSource.login(model)
        }.asFlow()

    override fun register(model: RegisterModel): Flow<Resource<Boolean>> =
        object : NetworkBoundResource<Boolean, Boolean>() {
            override fun returnResult(data: Boolean): Flow<Boolean> = flow {
                emit(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<Boolean>> =
                remoteDataSource.register(ModelToRequest.toRegisterRequest(model))
        }.asFlow()

    override fun filterProduct(model: FilterModel): Flow<Resource<List<ProductModel>>> =
        object : NetworkBoundResource<List<ProductModel>, List<ProductsResponseItem>>() {
            override fun returnResult(data: List<ProductsResponseItem>): Flow<List<ProductModel>> =
                flow {
                    emit(ResponseToModel.toProductsModelList(data))
                }

            override suspend fun createCall(): Flow<ApiResponse<List<ProductsResponseItem>>> =
                remoteDataSource.filterProduct(model)
        }.asFlow()
}