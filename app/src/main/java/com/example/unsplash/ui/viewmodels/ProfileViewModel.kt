package com.example.unsplash.ui.viewmodels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash.R
import com.example.unsplash.data.auth.AuthRepository
import com.example.unsplash.data.network.UnsplashApiRepository
import com.example.unsplash.data.network.UnsplashPhotoDto
import com.example.unsplash.data.network.models.UserDto
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationService
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    application: Application,
    private val authRepository: AuthRepository,
    private val apiRepository: UnsplashApiRepository
): ViewModel() {

    private val _likedPhotos = MutableStateFlow<List<UnsplashPhotoDto>>(emptyList())
    val likedPhotos = _likedPhotos.asStateFlow()

    private val authService: AuthorizationService = AuthorizationService(getApplication(application))
    private val loadingMutableStateFlow = MutableStateFlow(false)
    private val userInfoMutableStateFlow = MutableStateFlow<UserDto?>(null)
    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    private val logoutPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val logoutCompletedEventChannel = Channel<Unit>(Channel.BUFFERED)

    fun submitUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                apiRepository.getUserLikedPhotos(username = username)
            }.fold(
                onSuccess = {
                    Log.d("ProfileViewModel", "submitUsername success: $it")
                    _likedPhotos.value = it
                }, onFailure = {
                    Log.d("ProfileViewModel", "submitUsername error: ${it.message}")
                }
            )
        }
    }

    val userInfoFlow: Flow<UserDto?>
        get() = userInfoMutableStateFlow.asStateFlow()

    val toastFlow: Flow<Int>
        get() = toastEventChannel.receiveAsFlow()

    val logoutPageFlow: Flow<Intent>
        get() = logoutPageEventChannel.receiveAsFlow()

    val logoutCompletedFlow: Flow<Unit>
        get() = logoutCompletedEventChannel.receiveAsFlow()

    fun loadUserInfo() {
        viewModelScope.launch {
            loadingMutableStateFlow.value = true
            runCatching {
                apiRepository.getCurrentUser()
            }.onSuccess {
                userInfoMutableStateFlow.value = it
                loadingMutableStateFlow.value = false
            }.onFailure {
                Log.d("GETUSER", it.toString())
                loadingMutableStateFlow.value = false
                userInfoMutableStateFlow.value = null
                toastEventChannel.trySendBlocking(R.string.get_user_error)
            }
        }
    }

    fun logout() {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val logoutPageIntent = authService.getEndSessionRequestIntent(
            authRepository.getEndSessionRequest(),
            customTabsIntent
        )

        logoutPageEventChannel.trySendBlocking(logoutPageIntent)
    }

    fun webLogoutComplete() {
        authRepository.logout()
        logoutCompletedEventChannel.trySendBlocking(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}