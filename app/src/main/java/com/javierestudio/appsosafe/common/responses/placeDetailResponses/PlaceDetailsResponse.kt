package com.javierestudio.appsosafe.common.responses.placeDetailResponses

import com.google.gson.annotations.SerializedName

data class PlaceDetailsResponse(
    @SerializedName("html_attributions") var htmlAttributionsPlaceDetails: List<Any> = listOf(),
    @SerializedName("result") var result: ResultPlaceDetailsResponse? = null,
    @SerializedName("status") var status: String
)
