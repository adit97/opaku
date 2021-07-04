package com.opaku.id.core.utils.mapper

import com.opaku.id.core.data.source.remote.response.*
import com.opaku.id.core.domain.model.*

object ResponseToModel {
    fun toProductsModelList(response: ProductsResponse): List<ProductModel> {
        val listModel = mutableListOf<ProductModel>()
        response.forEach {
            it.apply {
                listModel.add(
                    ProductModel(
                        id = id,
                        name = name,
                        rate = rate.toFloat(),
                        category = category,
                        weight = weight,
                        preview = preview,
                        brand = brand,
                        desc = desc,
                        variantPriceModel = toVariantPriceModelList(variantPrice),
                        variant = toVariantModelList(variant),
                        color = toColorModelList(color),
                        reviewModel = toReviewModelList(reviews)
                    )
                )
            }
        }

        return listModel
    }

    fun toProductsModelList(response: List<ProductsResponseItem>): List<ProductModel> {
        val listModel = mutableListOf<ProductModel>()
        response.forEach {
            it.apply {
                listModel.add(
                    ProductModel(
                        id = id,
                        name = name,
                        rate = rate.toFloat(),
                        category = category,
                        weight = weight,
                        preview = preview,
                        brand = brand,
                        desc = desc,
                        variantPriceModel = toVariantPriceModelList(variantPrice),
                        variant = toVariantModelList(variant),
                        color = toColorModelList(color),
                        reviewModel = toReviewModelList(reviews)
                    )
                )
            }
        }

        return listModel
    }

    fun toVariantPriceModelList(response: List<VariantPrice>): List<ProductVariantPriceModel> {
        val listModel = mutableListOf<ProductVariantPriceModel>()
        response.forEach {
            it.apply {
                listModel.add(
                    ProductVariantPriceModel(
                        size = ProductVariantModel(size = size),
                        color = ProductColorModel(color = color),
                        image = image,
                        price = price,
                        originalPrice = originalPrice,
                        discount = discount
                    )
                )
            }
        }
        return listModel
    }

    private fun toVariantModelList(response: List<String>): List<ProductVariantModel> {
        val listModel = mutableListOf<ProductVariantModel>()
        response.forEach {
            listModel.add(ProductVariantModel(size = it))
        }

        if (listModel.isNotEmpty()) {
            listModel[0].isSelected = true
        }

        return listModel
    }

    private fun toColorModelList(response: List<String>): List<ProductColorModel> {
        val listModel = mutableListOf<ProductColorModel>()
        response.forEach {
            listModel.add(ProductColorModel(color = it))
        }

        if (listModel.isNotEmpty()) {
            listModel[0].isSelected = true
        }

        return listModel
    }

    private fun toReviewModelList(response: List<Review>): List<ProductReviewModel> {
        val listModel = mutableListOf<ProductReviewModel>()
        response.forEach {
            it.apply {
                listModel.add(
                    ProductReviewModel(
                        user = user,
                        rate = rate.toFloat(),
                        review = review,
                        date = date,
                        images = images
                    )
                )
            }
        }

        return listModel
    }

    fun toCartModel(response: CartResponseItem): CartModel {
        val model: CartModel
        response.apply {
            model = CartModel(
                    user = user,
                    cart = toCartItemModelList(cart)
                )
        }
        return model
    }

    private fun toCartItemModelList(response: List<Cart>): List<CartItemModel> {
        val listModel = mutableListOf<CartItemModel>()
        response.forEach {
            it.apply {
                listModel.add(
                    CartItemModel(
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
        return listModel
    }
}