package com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses

import com.google.gson.annotations.SerializedName

data class NortheastResponse(
    @SerializedName("lat") var lat: Double,
    @SerializedName("lng") var lng: Double
)
