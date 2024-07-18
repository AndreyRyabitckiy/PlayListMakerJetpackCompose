package com.example.playlistmakerjetpackcompose.settings.domain

interface SettingsInteractor {
    fun getThemeSettings(): Int
    fun updateThemeSetting(theme: Int)
}