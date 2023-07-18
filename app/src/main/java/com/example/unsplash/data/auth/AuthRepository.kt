package com.example.unsplash.data.auth

import android.net.Uri
import androidx.core.net.toUri
import com.example.unsplash.ACCESS_KEY
import com.example.unsplash.SECRET_KEY
import net.openid.appauth.*
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class AuthRepository @Inject constructor(){

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(URL_AUTHORIZATION),
        Uri.parse(URL_TOKEN_EXCHANGE),
        null, //registration endpoint
        Uri.parse(URL_LOGOUT_REDIRECT)
    )

    /* generates an authorization request to start the authentication process */
    fun getAuthRequest(): AuthorizationRequest {
        val redirectUri = REDIRECT_URI.toUri()
        return AuthorizationRequest.Builder(
            serviceConfiguration, ACCESS_KEY, RESPONSE_TYPE, redirectUri
        ).setScope(SCOPE).build()
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(SECRET_KEY)
    }

    fun getRefreshTokenRequest(refreshToken: String): TokenRequest {
        return TokenRequest.Builder(
            serviceConfiguration,
            ACCESS_KEY
        )
            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
            .setScopes(SCOPE)
            .setRefreshToken(refreshToken)
            .build()
    }

    suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest
    ): TokensModel {
        return suspendCoroutine { continuation ->
            authService.performTokenRequest(
                tokenRequest,
                getClientAuthentication()
            ) { response, ex ->
                when {
                    // exchange token is successful
                    response != null -> {

                        val tokens = TokensModel(
                            accessToken = response.accessToken.orEmpty(),
                            refreshToken = response.refreshToken.orEmpty(),
                            idToken = response.idToken.orEmpty()
                        )
                        TokenStorage.accessToken = tokens.accessToken
                        TokenStorage.refreshToken = tokens.refreshToken
                        TokenStorage.idToken = tokens.idToken

                        continuation.resumeWith(Result.success(tokens))
                    }
//                     exchange token is failed
                    ex != null -> continuation.resumeWith(Result.failure(ex))
                    else -> error("unreachable")
                }
            }
        }
    }

    fun logout() {
        TokenStorage.accessToken = null
        TokenStorage.refreshToken = null
        TokenStorage.idToken = null
    }

    fun getEndSessionRequest(): EndSessionRequest {
        return EndSessionRequest.Builder(serviceConfiguration)
            .setPostLogoutRedirectUri(URL_LOGOUT.toUri())
            .build()
    }

    companion object {
        // constants for authorization process
        const val URL_AUTHORIZATION = "https://unsplash.com/oauth/authorize" //AUTH_URI
        const val URL_TOKEN_EXCHANGE = "https://unsplash.com/oauth/token" //TOKEN_URI
        const val URL_LOGOUT_REDIRECT = "https://unsplash.com/logout" //END_SESSION_URI
        const val RESPONSE_TYPE = ResponseTypeValues.CODE
        const val SCOPE = "public read_user write_user read_photos write_photos write_likes write_followers read_collections write_collections"
        const val REDIRECT_URI = "com.example.unsplash://unsplash_redirect" //CALLBACK_URL
        const val URL_LOGOUT = "com.example.unsplash://unsplash_redirect" //LOGOUT_CALLBACK_URL
    }
}