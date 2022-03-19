package com.example.favorite.di

import com.example.favorite.ui.FavoriteVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val favoriteModule = module{
    viewModel{ FavoriteVM(get()) }
}