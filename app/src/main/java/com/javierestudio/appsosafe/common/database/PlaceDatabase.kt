package com.javierestudio.appsosafe.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.javierestudio.appsosafe.common.entities.PlaceEntity
import com.javierestudio.appsosafe.common.entities.ReviewsEntity

@Database(entities = [PlaceEntity::class, ReviewsEntity::class], version = 1)
@TypeConverters(DatabaseTypeConverters::class)
abstract class PlaceDatabase: RoomDatabase() {
    abstract fun placeDao():PlaceDao
}