package com.javierestudio.appsosafe

import android.app.Application
import androidx.room.Room
import com.javierestudio.appsosafe.common.database.PlaceDatabase

class MapApplication : Application() {
    companion object {
        lateinit var database: PlaceDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this,
            PlaceDatabase::class.java,
            "PlaceDatabase")
            .build()
    }
}