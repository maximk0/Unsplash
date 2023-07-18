package com.example.unsplash.data.room

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "unsplash_photos")
data class UnsplashPhotosEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "likes")
    val likes: Int,
    @ColumnInfo(name = "is_liked")
    val likedByUser: Boolean,
    @ColumnInfo(name = "url_photo")
    val urlPhoto: String,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "nickname")
    val userNickname: String,
    @ColumnInfo(name = "profile_image")
    val userPhoto: String,
    @ColumnInfo(name = "share_link")
    val shareLink: String
) : Parcelable
