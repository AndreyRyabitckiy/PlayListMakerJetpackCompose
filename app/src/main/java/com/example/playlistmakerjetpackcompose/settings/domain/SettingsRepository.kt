package com.example.playlistmakerjetpackcompose.settings.domain

interface SettingsRepository {
    fun getThemeSettings(): Int

    fun updateThemeSetting(theme: Int)

    fun getAppShareLink(): String

    fun getUserPolicy(): String

    fun getSupportEmail(): String
    fun getSupportEmailTop(): String
    fun getSupportEmailText(): String
}