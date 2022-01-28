package com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses

import com.google.gson.annotations.SerializedName

data class PoisResponse (

    @SerializedName("html_attributions") var htmlAttributions: List<Any>? = listOf(),
    @SerializedName("next_page_token") var nextPageToken: String,
    @SerializedName("results") var results: List<ResultResponse> = listOf(),
    @SerializedName("status") var status: String

)