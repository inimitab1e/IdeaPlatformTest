package com.inimitable.ideaplatformtest.domain.repository

import com.inimitable.ideaplatformtest.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun watchProducts(query: String): Flow<List<Product>>

    suspend fun deleteProduct(id: Int)

    suspend fun changeProductAmount(id: Int, newAmountValue: Int)
}