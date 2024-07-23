package com.example.playlistmakerjetpackcompose.settings.domain

interface SettingsInteractor {
    suspend fun getThemeSettings(): Boolean
    fun updateThemeSetting(theme: Boolean)
}