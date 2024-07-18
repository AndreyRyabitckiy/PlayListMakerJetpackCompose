package com.example.playlistmakerjetpackcompose.settings.domain.impl

import com.example.playlistmakerjetpackcompose.settings.domain.SettingsInteractor
import com.example.playlistmakerjetpackcompose.settings.domain.SettingsRepository

class SettingsInteractorImpl(private val repository: SettingsRepository) : SettingsInteractor {

    override fun getThemeSettings(): Int = repository.getThemeSettings()

    override fun updateThemeSetting(theme: Int) = repository.updateThemeSetting(theme)

}