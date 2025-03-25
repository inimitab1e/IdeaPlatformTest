package com.inimitable.ideaplatformtest.presentation.productsList

import com.inimitable.ideaplatformtest.data.model.Product

data class ProductListState(
    val searchQuery: String = "",
    val products: List<Product> = listOf()
)