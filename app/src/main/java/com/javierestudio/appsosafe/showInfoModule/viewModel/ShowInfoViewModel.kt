package com.javierestudio.appsosafe.showInfoModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierestudio.appsosafe.common.entities.PlaceEntity
import com.javierestudio.appsosafe.common.responses.placeDetailResponses.PlaceDetailsResponse
import com.javierestudio.appsosafe.common.utils.Constants
import com.javierestudio.appsosafe.common.utils.DataException
import com.javierestudio.appsosafe.common.utils.TypeError
import com.javierestudio.appsosafe.showInfoModule.model.ShowInfoInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShowInfoViewModel: ViewModel() {

    private val interactor = ShowInfoInteractor()

    private val placesDetails = MutableLiveData<PlaceDetailsResponse>()
    private val isVisible = MutableLiveData<Boolean>()
    private val typeError: MutableLiveData<TypeError> = MutableLiveData()

    fun getAllPlaceDetails(placeId: String){

        viewModelScope.launch {
            isVisible.value = Constants.SHOW
            try {
                interactor.getPlaceDetails(placeId) { placeDetailsResponse ->
                    placesDetails.postValue(placeDetailsResponse)
                }
            } catch (e: DataException){
                typeError.value = e.typeError
            } finally {
                isVisible.value = Constants.HIDE
            }
        }
    }

    fun saveFavoritePlace(placeEntity: PlaceEntity){
        executeAction{
            interactor.saveFavoritePlace(placeEntity)
        }
    }

    fun deleteFavoritePlace(placeEntity: PlaceEntity) {
        executeAction { interactor.deleteFavoritePlace(placeEntity) }
    }

    fun setTypeError(typeError: TypeError){
        this.typeError.value = typeError
    }

    fun getTypeError(): MutableLiveData<TypeError> = typeError

    fun setPlacesDetails(placeDetailsList: PlaceDetailsResponse){
        placesDetails.value = placeDetailsList
    }

    fun getPlaceDetails(): LiveData<PlaceDetailsResponse> = placesDetails

    fun setShowProggresBar(isVisible : Boolean){
        this.isVisible.value = isVisible
    }

    fun isShowProggresBar(): LiveData<Boolean> = isVisible

    private fun executeAction(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (e: DataException) {
                typeError.value = e.typeError
            }
        }
    }

}