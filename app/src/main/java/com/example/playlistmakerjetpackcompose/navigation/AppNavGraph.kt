package com.example.playlistmakerjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    searchScreenContent: @Composable () -> Unit,
    mediaScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.MediaScreen.route
    ){
        composable(Screen.SearchScreen.route){
            searchScreenContent()
        }

        composable(Screen.MediaScreen.route){
            mediaScreenContent()
        }

        composable(Screen.SettingsScreen.route){
            settingsScreenContent()
        }
    }
}