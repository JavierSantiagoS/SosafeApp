package com.javierestudio.appsosafe.mapModule.model

import com.google.android.gms.maps.model.LatLng
import com.javierestudio.appsosafe.common.database.APIService
import com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses.PoisResponse
import com.javierestudio.appsosafe.common.utils.Constants
import com.javierestudio.appsosafe.common.utils.DataException
import com.javierestudio.appsosafe.common.utils.RetrofitHelper
import com.javierestudio.appsosafe.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MapInteractor {


    suspend fun getPOIs(query: String, coordinates: LatLng, response: (PoisResponse?) -> Unit) =
        withContext(Dispatchers.IO) {
            val url: String =
                Constants.URL_NEARBY_SEARCH + Constants.URL_TYPE_GET + Constants.URL_API_KEY +
                        Constants.URL_LOCATION + coordinates.latitude.toString() +
                        Constants.COMA + coordinates.longitude.toString() +
                        Constants.URL_RADIUS + Constants.URL_TYPE + query

            val call = RetrofitHelper.getRetrofit().create(APIService::class.java)
                .getAllFilms(url)
            when (call.code()) {
                Constants.TIME_OUT -> throw DataException(TypeError.NET_OUT)
                Constants.CALL_SUCCES -> {
                    val pois = call.body()
                    response(pois)
                }
                Constants.BAD_REQUEST -> throw DataException(TypeError.GET)
                else -> throw DataException(TypeError.UNKNOWN_ERROR)
            }
        }

}