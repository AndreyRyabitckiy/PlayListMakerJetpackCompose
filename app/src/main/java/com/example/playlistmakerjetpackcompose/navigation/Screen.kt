package com.example.playlistmakerjetpackcompose.navigation

sealed class Screen(
    val route: String
) {
    object SearchScreen : Screen(ROTE_SEARCH_SCREEN)
    object MediaScreen : Screen(ROTE_MEDIA_SCREEN)
    object SettingsScreen : Screen(ROTE_SETTINGS_SCREEN)
    object CreateScreen: Screen(ROTE_CREATE_SCREEN)

    private companion object {
        const val ROTE_SEARCH_SCREEN = "search_screen"
        const val ROTE_MEDIA_SCREEN = "media_screen"
        const val ROTE_SETTINGS_SCREEN = "settings_screen"
        const val ROTE_CREATE_SCREEN = "create_screen"
    }
}