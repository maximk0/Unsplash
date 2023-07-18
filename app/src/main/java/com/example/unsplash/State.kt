package com.example.unsplash

sealed class State {
    object Loading : State()
    object Success : State()
    object Error : State()
}