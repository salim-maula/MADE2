package com.example.capstone.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Developer
import com.example.core.domain.usecase.DeveloperUseCase
import kotlinx.coroutines.launch

class DetailVM(private val deceloperUseCase: DeveloperUseCase) : ViewModel() {

    fun checkDeveloper(id: Int) = deceloperUseCase.check(id)?.asLiveData()

    fun getDetailDeveloper(username: String) = deceloperUseCase.getDetailDevelper(username).asLiveData()

    fun addToFavorite(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?) =
        viewModelScope.launch {
            deceloperUseCase.insert(username, id, avatarUrl, isFavorite)
        }

    fun removeFromFavorite(developer: Developer) = viewModelScope.launch {
        deceloperUseCase.delete(developer)
    }
}