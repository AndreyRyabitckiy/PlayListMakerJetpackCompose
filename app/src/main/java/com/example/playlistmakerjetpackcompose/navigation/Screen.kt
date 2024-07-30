package com.example.playlistmakerjetpackcompose.navigation

import android.net.Uri
import com.example.playlistmakerjetpackcompose.playlist_create.domain.models.PlayList
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import com.google.gson.Gson

sealed class Screen(
    val route: String
) {
    data object SearchScreen : Screen(ROTE_SEARCH_SCREEN)
    data object MediaScreen : Screen(ROTE_MEDIA_SCREEN)
    data object SettingsScreen : Screen(ROTE_SETTINGS_SCREEN)
    data object CreateScreen : Screen(ROTE_CREATE_SCREEN) {
        private const val ROUTE_FOR_ARGS_PLAYLIST = "create_screen"
        fun getRouteWithArgs(playList: PlayList?): String {
            val gson = Gson().toJson(playList)
            return "$ROUTE_FOR_ARGS_PLAYLIST/${gson.encode()}"
        }
    }

    data object PlayerScreen : Screen(ROTE_PLAYER_SCREEN) {
        private const val ROUTE_FOR_ARGS_TRACK = "player_screen"
        fun getRouteWithArgs(track: Track): String {
            val gson = Gson().toJson(track)
            return "$ROUTE_FOR_ARGS_TRACK/${gson.encode()}"
        }
    }

    data object AboutPlayListScreen : Screen(ROTE_ABOUT_SCREEN) {

        private const val ROUTE_FOR_ARGS = "about_screen"
        fun getRouteWithArgs(id: String): String {
            return "$ROUTE_FOR_ARGS/${id}"
        }
    }

    companion object {
        const val KEY_PLAYLIST = "playlist"
        const val KEY_PLAYER = "player"
        const val ROTE_PLAYER_SCREEN = "player_screen/{$KEY_PLAYER}"
        const val ROTE_ABOUT_SCREEN = "about_screen/{about_playlist_id}"
        const val ROTE_SEARCH_SCREEN = "search_screen"
        const val ROTE_MEDIA_SCREEN = "media_screen"
        const val ROTE_SETTINGS_SCREEN = "settings_screen"
        const val ROTE_CREATE_SCREEN = "create_screen/{$KEY_PLAYLIST}"
    }
}

fun String.encode(): String {
    return Uri.encode(this)
}