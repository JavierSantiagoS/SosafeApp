package com.javierestudio.appsosafe.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "PlaceEntity")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) var idIntern:Long = 0L,
    var coordinates: LatLng,
    var name: String,
    var vicinity: String,
    var rating: Double,
    var isFavorite: Boolean,
    var photoPlace: String,
    var reviews: List<ReviewsEntity> = listOf()
)
