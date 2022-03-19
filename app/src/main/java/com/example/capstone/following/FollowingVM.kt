package com.example.capstone.following

import androidx.lifecycle.*
import com.example.core.data.source.Resource
import com.example.core.domain.model.Developer
import com.example.core.domain.usecase.DeveloperUseCase

class FollowingVM(developerUseCase: DeveloperUseCase) : ViewModel() {
    private var username: MutableLiveData<String> = MutableLiveData()

    val developerFollowing: LiveData<Resource<List<Developer>>> =
        Transformations.switchMap(username) {
            developerUseCase.getListFollowing(it).asLiveData()
        }

    fun setFollowing(user: String) {
        if (username.value == user) return

        username.value = user
    }


}