package com.example.unsplash.ui.viewmodels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash.R
import com.example.unsplash.TAG_AUTH
import com.example.unsplash.data.auth.AuthRepository
import com.example.unsplash.data.SharedPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val authRepository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {

    private val authService = AuthorizationService(getApplication())

    private val openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    val openAuthPageFlow: Flow<Intent>
        get() = openAuthPageEventChannel.receiveAsFlow()

    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    val toastFlow: Flow<Int>
        get() = toastEventChannel.receiveAsFlow()

    private val authSuccessEventChannel = Channel<Unit>(Channel.BUFFERED)
    val authSuccessFlow: Flow<Unit>
        get() = authSuccessEventChannel.receiveAsFlow()

    private val _isAuthorized = MutableStateFlow(false)
    val isAuthorized = _isAuthorized.asStateFlow()

    init {
        _isAuthorized.value = sharedPrefsRepository.getAuthState()
    }

    /* save authorized state (authorized / not authorized) and success token */
    private fun saveAuthStateAndToken(isAuth: Boolean) {
        _isAuthorized.value = isAuth
        sharedPrefsRepository.saveAuthState(_isAuthorized.value)
    }

    /* if auth fails */
    fun onAuthCodeFailed(exception: AuthorizationException) {
        toastEventChannel.trySendBlocking(R.string.auth_failed)
    }

    /* if auth is success */
    fun onAuthCodeSuccess(tokenRequest: TokenRequest) {
        Log.d(TAG_AUTH, "3. Received code = ${tokenRequest.authorizationCode}")

        viewModelScope.launch {
            kotlin.runCatching {
                Log.d(TAG_AUTH, "4. Change code to token. Url = ${tokenRequest.configuration.tokenEndpoint}")

                authRepository.performTokenRequest(authService, tokenRequest)
            }.onSuccess {
                saveAuthStateAndToken(true)
                authSuccessEventChannel.send(Unit)
            }.onFailure {
                saveAuthStateAndToken(false)
                toastEventChannel.send(R.string.auth_failed)
            }
        }
    }

    /* opens a sign-in page in the browser and sends the authorization request */
    fun openSignInPage() {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val authRequest = authRepository.getAuthRequest()

        Log.d(
            TAG_AUTH,
            "1. Generated verifier = ${authRequest.codeVerifier}, challenge = ${authRequest.codeVerifierChallenge}"
        )

        val openAuthPageIntent =
            authService.getAuthorizationRequestIntent(authRequest, customTabsIntent)

        openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)

        Log.d(TAG_AUTH, "2. Open auth page: ${authRequest.toUri()}")
    }

    /* prevent leak */
    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}