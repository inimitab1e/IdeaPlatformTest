package com.inimitable.ideaplatformtest.data.local

import androidx.room.Dao
import androidx.room.Query
import com.inimitable.ideaplatformtest.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SELECT * FROM item")
    fun watchProducts(): Flow<List<Product>>

    @Query("SELECT * FROM item WHERE name LIKE :query")
    fun watchProductsWithQuery(query: String): Flow<List<Product>>

    @Query("UPDATE item SET amount = :newAmountValue WHERE id =:id")
    suspend fun changeAmountValue(id: Int, newAmountValue: Int)

    @Query("DELETE FROM item WHERE id = :id")
    suspend fun deleteProduct(id: Int)
}