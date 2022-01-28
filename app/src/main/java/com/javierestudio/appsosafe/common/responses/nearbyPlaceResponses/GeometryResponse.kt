package com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses

import com.google.gson.annotations.SerializedName

data class GeometryResponse (
    @SerializedName("location") var location: LocationResponse,
    @SerializedName("viewport") var viewport: ViewportResponse
)