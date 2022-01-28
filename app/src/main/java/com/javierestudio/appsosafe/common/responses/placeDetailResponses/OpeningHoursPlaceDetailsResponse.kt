package com.javierestudio.appsosafe.common.responses.placeDetailResponses

import com.google.gson.annotations.SerializedName

data class OpeningHoursPlaceDetailsResponse(
    @SerializedName("open_now") var openNow: Boolean,
    @SerializedName("periods") var periods: List<PeriodResponse> = listOf(),
    @SerializedName("weekday_text") var weekdayText: List<String> = listOf()
)
