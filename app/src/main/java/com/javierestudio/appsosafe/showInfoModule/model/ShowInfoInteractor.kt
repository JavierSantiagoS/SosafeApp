package com.javierestudio.appsosafe.showInfoModule.model

import android.database.sqlite.SQLiteConstraintException
import com.javierestudio.appsosafe.MapApplication
import com.javierestudio.appsosafe.common.database.APIService
import com.javierestudio.appsosafe.common.entities.PlaceEntity
import com.javierestudio.appsosafe.common.responses.placeDetailResponses.PlaceDetailsResponse
import com.javierestudio.appsosafe.common.utils.Constants
import com.javierestudio.appsosafe.common.utils.DataException
import com.javierestudio.appsosafe.common.utils.RetrofitHelper
import com.javierestudio.appsosafe.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShowInfoInteractor {

    suspend fun getPlaceDetails(placeId: String, response: (PlaceDetailsResponse?) -> Unit) =
        withContext(Dispatchers.IO) {
            val url: String = Constants.URL_PLACE_DETAILS + Constants.URL_TYPE_GET +
                    Constants.URL_API_KEY + Constants.URL_PLACE_ID + placeId

            val call = RetrofitHelper.getRetrofit().create(APIService::class.java)
                .getAllPlaceDetails(url)
            when (call.code()) {
                Constants.TIME_OUT -> throw DataException(TypeError.NET_OUT)
                Constants.CALL_SUCCES -> {
                    val placesDetails = call.body()
                    response(placesDetails)
                }
                Constants.BAD_REQUEST -> throw DataException(TypeError.GET)
                else -> throw DataException(TypeError.UNKNOWN_ERROR)
            }
        }

    suspend fun saveFavoritePlace(placeEntity: PlaceEntity) = withContext(Dispatchers.IO) {
        try {
            MapApplication.database.placeDao().addFavoritePlace(placeEntity)
        } catch (e: SQLiteConstraintException) {
            throw DataException(TypeError.INSERT)
        }
    }

    suspend fun deleteFavoritePlace(placeEntity: PlaceEntity) = withContext(Dispatchers.IO){
        val result = MapApplication.database.placeDao().deleteFavoritePlace(placeEntity)
        if( result == 0 ) throw DataException(TypeError.DELETE)
    }

}