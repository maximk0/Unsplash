package com.example.unsplash.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash.State
import com.example.unsplash.data.network.models.DetailPhotoDto
import com.example.unsplash.data.network.UnsplashApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsPhotoViewModel @Inject constructor(
    private val apiRepository: UnsplashApiRepository
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    private val _detailPhoto = MutableStateFlow<DetailPhotoDto?>(null)
    val detailPhoto = _detailPhoto.asStateFlow()


    fun getPhotoDetailsFromApi(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                apiRepository.getPhotoDetails(id)
            }.fold(
                onSuccess = {
                    Log.d("DetailsPhotoViewModel", it.toString())
                    _detailPhoto.value = it
                    _state.value = State.Success
                }, onFailure = {
                    Log.d("DetailsPhotoViewModel", it.message ?: "")
                    _state.value = State.Error
                }
            )
        }
    }

    fun onLikeClicked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                apiRepository.likePhoto(id)
            } catch (e: Exception) {
                Log.d("DetailsPhotoViewModel", "onLikeClicked${e.message}")
            }
        }
    }

    fun onDislikeClicked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                apiRepository.unlikePhoto(id)
            } catch (e: Exception) {
                Log.d("DetailsPhotoViewModel", "onDislikeClicked${e.message}")
            }
        }
    }

}