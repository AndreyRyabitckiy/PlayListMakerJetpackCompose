package com.example.playlistmakerjetpackcompose.settings.domain.impl

import com.example.playlistmakerjetpackcompose.settings.domain.SettingsInteractor
import com.example.playlistmakerjetpackcompose.settings.domain.SettingsRepository

class SettingsInteractorImpl(private val repository: SettingsRepository) : SettingsInteractor {

    override suspend fun getThemeSettings(): Boolean = repository.getThemeSettings()

    override fun updateThemeSetting(theme: Boolean) = repository.updateThemeSetting(theme)

}