package com.example.unsplash.data.room

import javax.inject.Inject

class UnsplashDaoRepository @Inject constructor(private val dao: UnsplashPhotosDao) {

    suspend fun insert(photos: List<UnsplashPhotosEntity>) {
        dao.insert(photos)
    }

    fun getPagingSource() = dao.getPagingSource()

    suspend fun refresh(photos: List<UnsplashPhotosEntity>) {
        dao.refresh(photos)
    }
}
