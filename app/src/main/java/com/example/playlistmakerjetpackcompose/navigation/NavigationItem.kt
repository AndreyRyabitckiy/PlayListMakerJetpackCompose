package com.example.playlistmakerjetpackcompose.navigation

import com.example.playlistmakerjetpackcompose.R

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: Int
) {
    data object Search : NavigationItem(
        screen = Screen.SearchScreen,
        titleResId = R.string.search,
        icon = R.drawable.ic_search_bottom
    )

    data object Media : NavigationItem(
        screen = Screen.MediaScreen,
        titleResId = R.string.media,
        icon = R.drawable.ic_media
    )

    data object Settings : NavigationItem(
        screen = Screen.SettingsScreen,
        titleResId = R.string.settings,
        icon = R.drawable.ic_settings
    )
}