package com.javierestudio.appsosafe.favoriteModule.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.javierestudio.appsosafe.MapApplication
import com.javierestudio.appsosafe.common.entities.PlaceEntity

class FavoriteInteractor {

    val favoritePlaces: LiveData<MutableList<PlaceEntity>> = liveData {
        val favoritePlacesLiveData = MapApplication.database.placeDao().getAllStores()
        emitSource(favoritePlacesLiveData.map { stores ->
            stores.toMutableList()
        })
    }

}