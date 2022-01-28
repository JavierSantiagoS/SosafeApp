package com.javierestudio.appsosafe.common.responses.placeDetailResponses

import com.google.gson.annotations.SerializedName

data class OpenResponse(
    @SerializedName("day") var day: Int,
    @SerializedName("time") var time: String
)
