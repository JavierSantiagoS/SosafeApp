package com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses

import android.location.Location
import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("lat") var lat: Double,
    @SerializedName("lng") var lng: Double
)
