package com.example.playlistmakerjetpackcompose.navigation

import com.example.playlistmakerjetpackcompose.R

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: String,
    val icon: Int
) {
    object Search : NavigationItem(
        screen = Screen.SearchScreen,
        titleResId = "Поиск",
        icon = R.drawable.ic_search_bottom
    )

    object Media : NavigationItem(
        screen = Screen.MediaScreen,
        titleResId = "Медиатека",
        icon = R.drawable.ic_media
    )

    object Settings : NavigationItem(
        screen = Screen.SettingsScreen,
        titleResId = "Настройки",
        icon = R.drawable.ic_settings
    )
}