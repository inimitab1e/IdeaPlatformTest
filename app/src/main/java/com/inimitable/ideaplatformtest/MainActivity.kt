package com.inimitable.ideaplatformtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.inimitable.ideaplatformtest.presentation.productsList.ProductListScreenRoot
import com.inimitable.ideaplatformtest.presentation.productsList.ProductsListViewModel
import com.inimitable.ideaplatformtest.ui.theme.IdeaPlatformTestTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IdeaPlatformTestTheme() {
                val viewmodel = koinViewModel<ProductsListViewModel>()
                ProductListScreenRoot(viewmodel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IdeaPlatformTestTheme {
        ProductListScreenRoot()
    }
}