@file:OptIn(ExperimentalMaterial3Api::class)

package com.inimitable.ideaplatformtest.presentation.productsList

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.inimitable.ideaplatformtest.R
import com.inimitable.ideaplatformtest.core.DateFormatter
import com.inimitable.ideaplatformtest.data.model.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductListScreenRoot(
    viewModel: ProductsListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    ProductListScreen(state) { action ->
        viewModel.onAction(action)
    }
}

@Composable
fun ProductListScreen(
    state: ProductListState,
    onAction: (ProductListActions) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.toolbar_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF19A4EC)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFE8E8E8))
                .padding(16.dp)
        ) {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = { newQuery ->
                    onAction(ProductListActions.SearchQueryChanged(newQuery))
                },
                onImeSearch = { keyboardController?.hide() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(
                    items = state.products,
                    key = { it.id }
                ) { product ->
                    ProductItem(
                        product = product,
                        onDelete = {
                            onAction(ProductListActions.DeleteProduct(product.id))
                        },
                        onEditAmount = { id, newAmountValue ->
                            onAction(
                                ProductListActions.EditAmount(
                                    id = id,
                                    newAmountValue = newAmountValue
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        label = { Text(stringResource(R.string.search_bar_hint)) },
        placeholder = { Text(text = stringResource(R.string.search_bar_hint)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Очистить")
                }
            }
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onImeSearch()
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
    )
}

@Composable
fun ProductItem(
    product: Product,
    onDelete: () -> Unit,
    onEditAmount: (Int, Int) -> Unit,
) {
    var showAmountDialog by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(start = 2.dp, top = 2.dp, bottom = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        showAmountDialog = true
                    }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF6200EE))
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }

            if (product.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    product.tags.forEach { tag ->
                        AssistChip(
                            onClick = { },
                            label = { Text(tag) },
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.in_storage_title),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(text = product.amount.toString())
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.date_title),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(text = DateFormatter.getDate(product.time))
                }
            }
        }
    }

    if (showAmountDialog) {
        ProductAmountDialog(
            initialAmount = product.amount,
            onDismiss = { showAmountDialog = false },
            onConfirm = { newAmount ->
                onEditAmount(product.id, newAmount)
                showAmountDialog = false
            }
        )
    }
}