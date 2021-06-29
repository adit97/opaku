package com.opaku.id.core.domain.usecase

import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.repository.IAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppInteractor @Inject constructor(private val appRepository: IAppRepository): AppUseCase {
    override fun getProducts(): Flow<Resource<List<ProductModel>>> = appRepository.getProducts()
}