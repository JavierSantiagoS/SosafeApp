package com.javierestudio.appsosafe.favoriteModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.javierestudio.appsosafe.common.entities.PlaceEntity
import com.javierestudio.appsosafe.favoriteModule.model.FavoriteInteractor

class FavoriteViewModel: ViewModel() {

    private val interactor = FavoriteInteractor()
    private val isVisible = MutableLiveData<Boolean>()

    private val stores = interactor.favoritePlaces

    fun getFavoritePlaces(): LiveData<MutableList<PlaceEntity>> {
        return stores
    }

    fun setShowProggresBar(isVisible : Boolean){
        this.isVisible.value = isVisible
    }

    fun isShowProggresBar(): LiveData<Boolean> = isVisible

}