package com.example.capstone.follower

import androidx.lifecycle.*
import com.example.core.data.source.Resource
import com.example.core.domain.model.Developer
import com.example.core.domain.usecase.DeveloperUseCase

class FollowerVM(developerUseCase: DeveloperUseCase) : ViewModel() {
    private var username: MutableLiveData<String> = MutableLiveData()

    val developerFollowers: LiveData<Resource<List<Developer>>> =
        Transformations.switchMap(username) {
            developerUseCase.getListFollowers(it).asLiveData()
        }

    fun setFollower(user: String) {
        if (username.value == user) return
        username.value = user
    }


}