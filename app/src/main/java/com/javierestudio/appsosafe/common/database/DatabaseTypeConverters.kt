package com.javierestudio.appsosafe.common.database

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.javierestudio.appsosafe.common.entities.ReviewsEntity


class DatabaseTypeConverters {

    @TypeConverter
    fun fromLatLng(coordinates: LatLng?): String? = Gson().toJson(coordinates)

    @TypeConverter
    fun toLatLng(string: String?):LatLng? = Gson().fromJson(string, object : TypeToken<LatLng?>(){}.type)

    @TypeConverter
    fun fromReviewList(reviewList: List<ReviewsEntity?>): String? = Gson().toJson(reviewList)

    @TypeConverter
    fun toReviewList(string: String?): List<ReviewsEntity?> = Gson().fromJson(string, object : TypeToken<List<ReviewsEntity?>>(){}.type)

}