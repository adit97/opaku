package com.opaku.id.core.domain.usecase

import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.*
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

    override fun login(model: UserModel): Flow<Resource<Long>> =
        appRepository.login(model)

    override fun register(model: RegisterModel): Flow<Resource<Boolean>> =
        appRepository.register(model)

    override fun filterProduct(model: FilterModel): Flow<Resource<List<ProductModel>>> =
        appRepository.filterProduct(model)

    override fun addCart(model: CartModel): Flow<Resource<Boolean>> =
        appRepository.addCart(model)

    override fun carts(userId: Long): Flow<Resource<CartModel>> =
        appRepository.carts(userId)

    override fun removeCart(model: CartModel): Flow<Resource<Boolean>> =
        appRepository.removeCart(model)
}