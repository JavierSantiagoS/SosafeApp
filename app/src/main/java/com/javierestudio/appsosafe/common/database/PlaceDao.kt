package com.javierestudio.appsosafe.common.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.javierestudio.appsosafe.common.entities.PlaceEntity

@Dao
interface PlaceDao {

    @Query("SELECT * FROM PlaceEntity")
    fun getAllStores(): LiveData<MutableList<PlaceEntity>>

    @Query("SELECT * FROM PlaceEntity where name = :name")
    suspend fun getPlaceByName(name : String) : PlaceEntity?

    @Insert
    suspend fun addFavoritePlace(placeEntity: PlaceEntity) : Long

    @Delete
    suspend fun deleteFavoritePlace(placeEntity: PlaceEntity): Int


}