package com.javierestudio.appsosafe.common.responses.placeDetailResponses

import com.google.gson.annotations.SerializedName
import com.javierestudio.appsosafe.common.entities.ReviewsEntity

data class ReviewResponse(
    @SerializedName("author_name") var authorName: String,
    @SerializedName("author_url") var authorUrl: String,
    @SerializedName("language") var language: String,
    @SerializedName("profile_photo_url") var profilePhotoUrl: String,
    @SerializedName("rating") var rating: Int,
    @SerializedName("relative_time_description") var relativeTimeDescription: String,
    @SerializedName("text") var text: String,
    @SerializedName("time") var time: Int
)

fun ReviewResponse.mapToDb(): ReviewsEntity {
    return ReviewsEntity(
        authorName = this.authorName,
        profilePhotoUrl = this.profilePhotoUrl,
        rating = this.rating,
        relativeTimeDescription = this.relativeTimeDescription,
        text = this.text
    )
}

fun List<ReviewResponse>.mapListToDb(): List<ReviewsEntity> {
    return this.map { it.mapToDb() }
}
