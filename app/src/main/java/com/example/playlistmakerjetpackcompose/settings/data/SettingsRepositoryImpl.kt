package com.example.playlistmakerjetpackcompose.settings.data

import android.content.Context
import com.example.playlistmakerjetpackcompose.settings.domain.SettingsRepository
import com.example.playlistmakerjetpackcompose.R

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    private val sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun getThemeSettings() =
        sharedPrefs.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

    override fun updateThemeSetting(theme: Int) =
        sharedPrefs.edit().putInt(THEME_KEY, theme).apply()


    override fun getAppShareLink() = context.getString(R.string.share_ht)

    override fun getUserPolicy() = context.getString(R.string.user_polic_web)

    override fun getSupportEmail() = context.getString(R.string.email)
    override fun getSupportEmailTop() = context.getString(R.string.email_subject)
    override fun getSupportEmailText() = context.getString(R.string.email_text)


    companion object {
        private const val PREF_NAME = "PREF_SETTINGS"
        private const val THEME_KEY = "THEME_KEY"
    }
}