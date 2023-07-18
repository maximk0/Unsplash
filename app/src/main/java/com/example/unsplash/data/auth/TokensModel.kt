package com.example.unsplash.data.auth

data class TokensModel(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)
