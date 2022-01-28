package com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses

import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("business_status") var businessStatus: String,
    @SerializedName("geometry") var geometry: GeometryResponse,
    @SerializedName("icon") var icon: String,
    @SerializedName("icon_background_color") var iconBackgroundColor: String,
    @SerializedName("icon_mask_base_uri") var iconMaskBaseUri: String,
    @SerializedName("name") var name: String,
    @SerializedName("opening_hours") var openingHours: OpeningHoursResponse,
    @SerializedName("place_id") var placeId: String,
    @SerializedName("plus_code") var plusCode: PlusCodeResponse,
    @SerializedName("rating") var rating: Double,
    @SerializedName("reference") var reference: String,
    @SerializedName("scope") var scope: String,
    @SerializedName("types") var types: List<String> = listOf(),
    @SerializedName("user_ratings_total") var userRatingsTotal: Int,
    @SerializedName("vicinity") var vicinity: String,
    @SerializedName("photos") var photos: List<PhotoResponse> = listOf(),
    @SerializedName("price_level") var priceLevel: Int
)
