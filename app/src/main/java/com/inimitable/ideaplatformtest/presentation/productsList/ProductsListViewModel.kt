package com.inimitable.ideaplatformtest.presentation.productsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inimitable.ideaplatformtest.domain.repository.ProductsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsListViewModel(
    private val productsRepository: ProductsRepository,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state

    init {
        _searchQuery
            .flatMapLatest { query ->
                productsRepository.watchProducts(query)
            }
            .onEach { products ->
                _state.update { currentState ->
                    currentState.copy(products = products)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ProductListActions) {
        when (action) {
            is ProductListActions.SearchQueryChanged -> {
                _state.update { it.copy(searchQuery = action.query) }
                _searchQuery.value = action.query
            }
            is ProductListActions.DeleteProduct -> {
                viewModelScope.launch {
                    productsRepository.deleteProduct(action.id)
                }
            }
            is ProductListActions.EditAmount -> {
                viewModelScope.launch {
                    productsRepository.changeProductAmount(
                        id = action.id,
                        newAmountValue = action.newAmountValue
                    )
                }
            }
        }
    }
}
