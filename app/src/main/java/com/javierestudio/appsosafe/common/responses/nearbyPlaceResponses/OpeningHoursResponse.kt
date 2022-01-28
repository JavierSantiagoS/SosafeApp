package com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses

import com.google.gson.annotations.SerializedName

data class OpeningHoursResponse(
    @SerializedName("open_now") var openNow: Boolean
)
