package com.javierestudio.appsosafe.common.database

import com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses.PoisResponse
import com.javierestudio.appsosafe.common.responses.placeDetailResponses.PlaceDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET
    suspend fun getAllFilms(@Url url: String): Response<PoisResponse>

    @GET
    suspend fun getAllPlaceDetails(@Url url: String): Response<PlaceDetailsResponse>

}