package com.example.capstone.di

import com.example.capstone.detail.DetailVM
import com.example.capstone.follower.FollowerVM
import com.example.capstone.following.FollowingVM
import com.example.capstone.home.SearchVM
import com.example.core.domain.usecase.DeveloperInteractor
import com.example.core.domain.usecase.DeveloperUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module{
    factory<DeveloperUseCase> { DeveloperInteractor(get()) }
}

val viewModelModule = module {
    viewModel { FollowerVM(get()) }
    viewModel { FollowingVM(get()) }
    viewModel { SearchVM(get()) }
    viewModel { DetailVM(get()) }
}