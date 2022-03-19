package com.example.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.DeveloperUseCase

class FavoriteVM(developerUseCase: DeveloperUseCase) : ViewModel() {
    val favoriteDevelopers = developerUseCase.getAllFavorites().asLiveData()
}