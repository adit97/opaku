package com.opaku.id.core.data

import com.opaku.id.core.data.source.local.LocalDataSource
import com.opaku.id.core.data.source.remote.RemoteDataSource
import com.opaku.id.core.data.source.remote.network.ApiResponse
import com.opaku.id.core.data.source.remote.response.ProductsResponse
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.repository.IAppRepository
import com.opaku.id.core.utils.AppExecutors
import com.opaku.id.core.utils.mapper.ResponseToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

}