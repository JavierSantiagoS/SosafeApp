package com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses

import com.google.gson.annotations.SerializedName

data class PlusCodeResponse(
    @SerializedName("global_code") var globalCode: String
)
