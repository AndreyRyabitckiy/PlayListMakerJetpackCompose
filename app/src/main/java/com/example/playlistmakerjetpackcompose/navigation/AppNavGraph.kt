package com.example.playlistmakerjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlistmakerjetpackcompose.media.presentation.compose_fun.MediaScreen
import com.example.playlistmakerjetpackcompose.playlist_create.presentation.compose_fun.CreatePlayListScreen
import com.example.playlistmakerjetpackcompose.search.presentation.compose_fun.SearchScreen
import com.example.playlistmakerjetpackcompose.settings.presentation.compose_fun.SettingsScreen

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    settingsScreenContent: @Composable () -> Unit,
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.MediaScreen.route
    ){
        composable(Screen.SearchScreen.route){
            SearchScreen()
        }

        composable(Screen.MediaScreen.route){
            MediaScreen(navHostController)
        }

        composable(Screen.SettingsScreen.route){
            settingsScreenContent()
        }

        composable(Screen.CreateScreen.route){
            CreatePlayListScreen()
        }
    }
}