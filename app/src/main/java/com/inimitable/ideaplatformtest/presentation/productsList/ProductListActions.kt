package com.inimitable.ideaplatformtest.presentation.productsList

sealed class ProductListActions {
    data class SearchQueryChanged(val query: String) : ProductListActions()
    data class DeleteProduct(val id: Int) : ProductListActions()
    data class EditAmount(val id: Int, val newAmountValue: Int) : ProductListActions()
}