package com.example.capstone.home

import androidx.lifecycle.*
import com.example.core.data.source.Resource
import com.example.core.domain.model.Developer
import com.example.core.domain.usecase.DeveloperUseCase

class SearchVM(developerUseCase: DeveloperUseCase) : ViewModel() {
    private var username: MutableLiveData<String> = MutableLiveData()

    val developers: LiveData<Resource<List<Developer>>> = Transformations
        .switchMap(username) {
            developerUseCase.getSearchDeveloper(it).asLiveData()
        }

    fun setSearch(query: String) {
        if (username.value == query) {
            return
        }
        username.value = query
    }


}