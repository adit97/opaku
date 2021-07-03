package com.opaku.id.core.domain.usecase

import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.CartModel
import com.opaku.id.core.domain.model.ProductModel
import com.opaku.id.core.domain.model.RegisterModel
import com.opaku.id.core.domain.model.UserModel
import com.opaku.id.core.domain.repository.IAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppInteractor @Inject constructor(private val appRepository: IAppRepository) : AppUseCase {
    override fun getProducts(): Flow<Resource<List<ProductModel>>> =
        appRepository.getProducts()

    override suspend fun addFavoriteProduct(productId: String) =
        appRepository.addFavoriteProduct(productId)

    override fun favoriteProducts(): Flow<List<String>> =
        appRepository.favoriteProducts()

    override fun deleteFavoriteProduct(productId: String) =
        appRepository.deleteFavoriteProduct(productId)

    override fun isFavoriteProduct(productId: String): Flow<String> =
        appRepository.isFavoriteProduct(productId)

    override suspend fun addChart(model: CartModel) =
        appRepository.addChart(model)

    override fun carts(): Flow<List<CartModel>> =
        appRepository.carts()

    override fun deleteCart(productId: String) =
        appRepository.deleteCart(productId)

    override fun login(model: UserModel): Flow<Resource<Boolean>> =
        appRepository.login(model)

    override fun register(model: RegisterModel): Flow<Resource<Boolean>> =
        appRepository.register(model)
}