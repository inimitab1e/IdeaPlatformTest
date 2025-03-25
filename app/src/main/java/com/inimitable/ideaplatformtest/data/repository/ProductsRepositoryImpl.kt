package com.inimitable.ideaplatformtest.data.repository

import com.inimitable.ideaplatformtest.data.local.ProductsDao
import com.inimitable.ideaplatformtest.data.model.Product
import com.inimitable.ideaplatformtest.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class ProductsRepositoryImpl(
    private val productsDao: ProductsDao,
) : ProductsRepository {

    override fun watchProducts(query: String): Flow<List<Product>> =
        if (query.isBlank())
            productsDao.watchProducts()
        else
            productsDao.watchProductsWithQuery("%$query%")

    override suspend fun deleteProduct(id: Int) {
        productsDao.deleteProduct(id = id)
    }

    override suspend fun changeProductAmount(id: Int, newAmountValue: Int) {
        productsDao.changeAmountValue(id = id, newAmountValue = newAmountValue)
    }
}