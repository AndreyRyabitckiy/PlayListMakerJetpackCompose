package com.example.playlistmakerjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlistmakerjetpackcompose.about_playlist.presentation.compose_fun.AboutPlayList
import com.example.playlistmakerjetpackcompose.media.presentation.compose_fun.MediaScreen
import com.example.playlistmakerjetpackcompose.player.presentation.compose_fun.PlayerScreen
import com.example.playlistmakerjetpackcompose.playlist_create.domain.models.PlayList
import com.example.playlistmakerjetpackcompose.playlist_create.presentation.compose_fun.CreatePlayListScreen
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import com.example.playlistmakerjetpackcompose.search.presentation.compose_fun.SearchScreen
import com.google.gson.Gson

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    settingsScreenContent: @Composable () -> Unit,
    createScreenContent: (PlayList?) -> Unit,
    onClickBack: () -> Unit,
    onClickSnackBar: (String) -> Unit,
    onClickItem: (Long) -> Unit,
    clickOnItemTrack: (Track) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MediaScreen.route
    ) {
        composable(Screen.SearchScreen.route) {
            SearchScreen {
                clickOnItemTrack(it)
            }
        }

        composable(Screen.MediaScreen.route) {
            MediaScreen(
                createOnClickContent = { createScreenContent(null) },
                onClickItem = { onClickItem(it) },
                onClickTrack = { clickOnItemTrack(it) }
            )
        }

        composable(Screen.SettingsScreen.route) {
            settingsScreenContent()
        }

        composable(Screen.CreateScreen.route) {
            val playListJson = it.arguments?.getString(Screen.KEY_PLAYLIST)
            if (playListJson != null) {
                val playList = Gson().fromJson(playListJson, PlayList::class.java)
                CreatePlayListScreen(
                    onClickBack = { onClickBack() },
                    onClickSave = { namePlayList ->
                        onClickSnackBar(namePlayList)
                    },
                    playlist = playList
                )
            } else {
                CreatePlayListScreen(
                    onClickBack = { onClickBack() },
                    onClickSave = { namePlayList ->
                        onClickSnackBar(namePlayList)
                    },
                    playlist = null
                )
            }
        }

        composable(Screen.AboutPlayListScreen.route) {
            val a = it.arguments?.getString("about_playlist_id") ?: ""
            AboutPlayList(id = a,
                onClickBack = { onClickBack() },
                onClickSnackBar = { onClickSnackBar(it) },
                onClickEditPlayList = { createScreenContent(it) },
                clickOnItem = { clickOnItemTrack(it) }
            )
        }

        composable(Screen.PlayerScreen.route) {
            val trackJson = it.arguments?.getString(Screen.KEY_PLAYER)
            val track = Gson().fromJson(trackJson, Track::class.java)
            PlayerScreen(
                track = track,
                onBackScreen = { onClickBack() },
                toastMessage = { onClickSnackBar(it) },
                onClickCreatePlayList = { createScreenContent(null) }
            )
        }
    }
}