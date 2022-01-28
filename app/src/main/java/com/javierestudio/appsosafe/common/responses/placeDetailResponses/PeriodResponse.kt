package com.javierestudio.appsosafe.common.responses.placeDetailResponses

import com.google.gson.annotations.SerializedName

data class PeriodResponse(
    @SerializedName("close") var close: CloseResponse,
    @SerializedName("open") var open: OpenResponse
)
