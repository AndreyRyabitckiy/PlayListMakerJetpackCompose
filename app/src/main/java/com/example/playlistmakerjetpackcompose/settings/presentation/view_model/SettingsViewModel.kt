package com.example.playlistmakerjetpackcompose.settings.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmakerjetpackcompose.settings.domain.SettingsInteractor
import com.example.playlistmakerjetpackcompose.settings.domain.SharingInteractor
import kotlinx.coroutines.runBlocking

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {


    private val _theme = MutableLiveData<Boolean>()
    val theme: LiveData<Boolean>
        get() = _theme


    private val _link = MutableLiveData<String>()
    val link: LiveData<String>
        get() = _link

    init {
        getTheme()
    }

    fun updateTheme(theme: Boolean) {
        settingsInteractor.updateThemeSetting(theme)
        getTheme()
    }

    private fun getTheme() {
        runBlocking {
            _theme.postValue(settingsInteractor.getThemeSettings())
        }
    }

    fun shareApp() {
        sharingInteractor.shareApp()
    }

    fun sendToSupport() {
        sharingInteractor.openSupport()
    }

    fun userPolicy() {
        sharingInteractor.openTerms()
    }

    fun getTermsLink() = _link.postValue(
        sharingInteractor.getTermsLink()
    )

}