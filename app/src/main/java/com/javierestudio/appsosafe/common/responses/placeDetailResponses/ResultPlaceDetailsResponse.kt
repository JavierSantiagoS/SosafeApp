package com.javierestudio.appsosafe.common.responses.placeDetailResponses

import com.google.gson.annotations.SerializedName
import com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses.GeometryResponse
import com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses.PhotoResponse
import com.javierestudio.appsosafe.common.responses.nearbyPlaceResponses.PlusCodeResponse

data class ResultPlaceDetailsResponse(
    @SerializedName("address_components") var addressComponents: List<AddressComponentResponse> = listOf(),
    @SerializedName("adr_address") var adrAddress: String,
    @SerializedName("business_status") var businessStatus: String,
    @SerializedName("formatted_address") var formattedAddress: String,
    @SerializedName("formatted_phone_number") var formattedPhoneNumber: String,
    @SerializedName("geometry") var geometry: GeometryResponse,
    @SerializedName("icon") var icon: String,
    @SerializedName("icon_background_color") var iconBackgroundColor: String,
    @SerializedName("icon_mask_base_uri") var iconMaskBaseUri: String,
    @SerializedName("international_phone_number") var internationalPhoneNumber: String,
    @SerializedName("name") var name: String,
    @SerializedName("opening_hours") var openingHours: OpeningHoursPlaceDetailsResponse,
    @SerializedName("photos") var photosPlaceDetails: List<PhotoResponse> = listOf(),
    @SerializedName("place_id") var placeId: String,
    @SerializedName("plus_code") var plusCodePlaceDetails: PlusCodeResponse,
    @SerializedName("price_level") var priceLevel: Int,
    @SerializedName("rating") var rating: Double,
    @SerializedName("reference") var reference: String,
    @SerializedName("reviews") var reviews: List<ReviewResponse> = listOf(),
    @SerializedName("types") var types: List<String> = listOf(),
    @SerializedName("url") var url: String,
    @SerializedName("user_ratings_total") var userRatingsTotal: Int,
    @SerializedName("utc_offset") var utcOffset: Int,
    @SerializedName("vicinity") var vicinity: String,
    @SerializedName("website") var website: String
)