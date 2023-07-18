package com.example.unsplash.data.room

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UnsplashPhotosDao {

    /* Inserts (or replace by Id) the list of photos */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: List<UnsplashPhotosEntity>)

    /* Will be used for pagination loading of data from the database */
    @Query("SELECT * FROM unsplash_photos")
    fun getPagingSource(): PagingSource<Int, UnsplashPhotosEntity>

    /* Delete photos */
    @Query("DELETE FROM unsplash_photos ")
    fun deletePhoto()

    /* Delete old data and add new data */
    @Transaction
    suspend fun refresh(photos: List<UnsplashPhotosEntity>) {
        deletePhoto()
        insert(photos)
    }

    // Inserts a single photo entity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: UnsplashPhotosEntity)

    @Query("SELECT * FROM unsplash_photos")
    fun getAllPhotos(): Flow<List<UnsplashPhotosEntity>>

    @Query("SELECT * FROM unsplash_photos WHERE id = :id")
    fun getItemById(id: String): UnsplashPhotosEntity?

    @Update
    fun update(item: UnsplashPhotosEntity)

}
