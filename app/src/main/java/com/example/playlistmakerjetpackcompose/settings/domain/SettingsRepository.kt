package com.example.playlistmakerjetpackcompose.settings.domain

interface SettingsRepository {
    suspend fun getThemeSettings(): Boolean

    fun updateThemeSetting(theme: Boolean)

    fun getAppShareLink(): String

    fun getUserPolicy(): String

    fun getSupportEmail(): String
    fun getSupportEmailTop(): String
    fun getSupportEmailText(): String
}