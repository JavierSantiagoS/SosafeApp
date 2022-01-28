package com.javierestudio.appsosafe.common.responses.placeDetailResponses

import com.google.gson.annotations.SerializedName

data class AddressComponentResponse(
    @SerializedName("long_name") var longName: String,
    @SerializedName("short_name") var shortName: String,
    @SerializedName("types") var types: List<String> = listOf(),
)
