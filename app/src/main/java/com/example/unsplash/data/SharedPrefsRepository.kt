package com.example.unsplash.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

private const val AUTH_STATE_NAME = "auth state name"
private const val AUTH_STATE_KEY = "auth state key"

class SharedPrefsRepository @Inject constructor(
    private val context: Context
    ) {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(AUTH_STATE_NAME, Context.MODE_PRIVATE)
    }

    private val editor: SharedPreferences.Editor by lazy { prefs.edit() }
    private var authState: Boolean = false

    fun saveAuthState(authState: Boolean) {
        editor.putBoolean(AUTH_STATE_KEY, authState).apply()
        this.authState = authState
    }

    fun getAuthState(): Boolean {
        authState = prefs.getBoolean(AUTH_STATE_KEY, false)
        return authState
    }

}
