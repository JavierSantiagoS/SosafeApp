package com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("height") var height: Int,
    @SerializedName("html_attributions") var htmlAttributions: List<String> = listOf(),
    @SerializedName("photo_reference") var photoReference: String,
    @SerializedName("width") var width: Int

)
