package com.javierestudio.appsosafe.mapModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.javierestudio.appsosafe.common.entities.PlaceEntity
import com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses.PoisResponse
import com.javierestudio.appsosafe.common.utils.Constants
import com.javierestudio.appsosafe.common.utils.DataException
import com.javierestudio.appsosafe.common.utils.TypeError
import com.javierestudio.appsosafe.mapModule.model.MapInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MapViewModel: ViewModel() {

    private val interactor = MapInteractor()

    private var placeEntity = MutableLiveData<PlaceEntity>()

    private val pois = MutableLiveData<PoisResponse>()
    private val isVisible = MutableLiveData<Boolean>()
    private val typeError: MutableLiveData<TypeError> = MutableLiveData()


    fun getAllPOIs(query: String, coordinates: LatLng){

        viewModelScope.launch {
            isVisible.value = Constants.SHOW
            try {
                interactor.getPOIs(query, coordinates) { poisResponse ->
                    pois.postValue(poisResponse)
                }
            } catch (e: DataException){
                typeError.value = e.typeError
            } finally {
                isVisible.value = Constants.HIDE
            }
        }
    }

    fun getPlaceByName(name: String):LiveData<PlaceEntity>{
        viewModelScope.launch {
            isVisible.value = Constants.SHOW
            try {
              placeEntity.postValue(interactor.getPlaceByName(name))
            } catch (e: DataException){
                typeError.value = e.typeError
            } finally {
                isVisible.value = Constants.HIDE
            }
        }
        return placeEntity
    }

    fun setFavoritePlaceClicked(favoritePlace: PlaceEntity){
        placeEntity.value = favoritePlace
    }

    fun getFavoritePlaceClicked(): LiveData<PlaceEntity> {
        return placeEntity
    }

    fun setPOIs(poisList: PoisResponse){
        pois.value = poisList
    }

    fun getPOIs(): LiveData<PoisResponse> {
        return pois
    }

    fun setTypeError(typeError: TypeError){
        this.typeError.value = typeError
    }

    fun getTypeError(): MutableLiveData<TypeError> = typeError

    fun setShowProggresBar(isVisible : Boolean){
        this.isVisible.value = isVisible
    }

    fun isShowProggresBar(): LiveData<Boolean> = isVisible

}