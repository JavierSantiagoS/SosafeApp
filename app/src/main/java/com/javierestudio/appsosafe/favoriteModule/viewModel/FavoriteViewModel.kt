package com.javierestudio.appsosafe.favoriteModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierestudio.appsosafe.common.entities.PlaceEntity
import com.javierestudio.appsosafe.common.utils.DataException
import com.javierestudio.appsosafe.favoriteModule.model.FavoriteInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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