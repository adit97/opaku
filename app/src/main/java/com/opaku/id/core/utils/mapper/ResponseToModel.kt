package com.opaku.id.core.utils.mapper

import com.opaku.id.core.data.source.remote.response.ProductsResponse
import com.opaku.id.core.data.source.remote.response.Review
import com.opaku.id.core.data.source.remote.response.VariantPrice
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
                        rate = rate,
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
                        rate = rate,
                        review = review,
                        images = images
                    )
                )
            }
        }

        return listModel
    }
}