package com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses

import com.google.gson.annotations.SerializedName

data class ViewportResponse(
    @SerializedName("northeast") var northeast: NortheastResponse,
    @SerializedName("southwest") var southwest: SouthwestResponse
)
