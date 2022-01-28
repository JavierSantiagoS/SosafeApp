package com.javierestudio.appsosafe.common.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javierestudio.appsosafe.common.responses.placeDetailResponses.ReviewResponse

@Entity(tableName = "ReviewsEntity")
data class ReviewsEntity(
    @PrimaryKey var authorName: String,
    var profilePhotoUrl: String,
    var rating: Int,
    var relativeTimeDescription: String,
    var text: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel!!.writeString(authorName)
        parcel.writeString(profilePhotoUrl)
        parcel.writeInt(rating)
        parcel.writeString(relativeTimeDescription)
        parcel.writeString(text)
    }

    companion object CREATOR : Parcelable.Creator<ReviewsEntity> {
        override fun createFromParcel(parcel: Parcel?): ReviewsEntity {
            return ReviewsEntity(parcel!!)
        }

        override fun newArray(size: Int): Array<ReviewsEntity?> {
            return arrayOfNulls(size)
        }
    }

}

fun ReviewsEntity.mapToResponse(): ReviewResponse {
    return ReviewResponse(
        authorUrl = "",
        authorName = this.authorName,
        profilePhotoUrl = this.profilePhotoUrl,
        rating = this.rating,
        relativeTimeDescription = this.relativeTimeDescription,
        text = this.text,
        language = "",
        time = 0
    )
}

fun List<ReviewsEntity>.mapListToDb(): List<ReviewResponse> {
    return this.map { it.mapToResponse() }
}
