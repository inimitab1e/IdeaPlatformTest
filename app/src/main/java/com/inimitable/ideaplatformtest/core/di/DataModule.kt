package com.inimitable.ideaplatformtest.core.di

import com.inimitable.ideaplatformtest.domain.repository.ProductsRepository
import com.inimitable.ideaplatformtest.data.repository.ProductsRepositoryImpl
import com.inimitable.ideaplatformtest.presentation.productsList.ProductsListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::ProductsRepositoryImpl).bind<ProductsRepository>()
    viewModelOf(::ProductsListViewModel)
}