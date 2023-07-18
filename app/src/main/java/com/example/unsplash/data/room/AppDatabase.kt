package com.example.unsplash.data.room
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UnsplashPhotosEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getUnsplashPhotosEntity(): UnsplashPhotosDao
}
