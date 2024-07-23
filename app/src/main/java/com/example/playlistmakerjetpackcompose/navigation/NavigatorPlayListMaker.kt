package com.example.playlistmakerjetpackcompose.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.media.presentation.compose_fun.MediaScreen
import com.example.playlistmakerjetpackcompose.search.presentation.compose_fun.SearchScreen
import com.example.playlistmakerjetpackcompose.settings.presentation.compose_fun.SettingsScreen
import com.example.playlistmakerjetpackcompose.settings.presentation.view_model.SettingsViewModel
import com.example.playlistmakerjetpackcompose.ui.theme.PlayListMakerJetpackComposeTheme
import com.example.playlistmakerjetpackcompose.ui.theme.YsRegularFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigatorPlayListMaker (viewModel: SettingsViewModel = koinViewModel()){
    val viewModelTheme = viewModel.theme.observeAsState(false)
    PlayListMakerJetpackComposeTheme(darkTheme = viewModelTheme.value) {
        val navHostController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    modifier = Modifier.height(70.dp),
                    backgroundColor = MaterialTheme.colorScheme.background
                ) {
                    val items = listOf(
                        NavigationItem.Search,
                        NavigationItem.Media,
                        NavigationItem.Settings
                    )
                    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    items.forEachIndexed() { index, item ->
                        BottomNavigationItem(
                            label = {
                                if (currentRoute == item.screen.route) {
                                    Text(
                                        text = item.titleResId,
                                        fontFamily = YsRegularFamily,
                                        color = colorResource(id = R.color.yp_blue)
                                    )
                                } else {
                                    Text(
                                        text = item.titleResId,
                                        fontFamily = YsRegularFamily,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            },
                            selected = currentRoute == item.screen.route,
                            onClick = { navHostController.navigate(item.screen.route) },
                            icon = {
                                if (currentRoute == item.screen.route) {
                                    Icon(
                                        painter = painterResource(id = item.icon),
                                        contentDescription = null,
                                        tint = colorResource(id = R.color.yp_blue)
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = item.icon),
                                        contentDescription = null,
                                    )
                                }
                            },
                            selectedContentColor = MaterialTheme.colorScheme.background,
                            unselectedContentColor = Color.Red
                        )
                    }
                }
            }
        ) { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                AppNavGraph(
                    navHostController = navHostController,
                    searchScreenContent = { SearchScreen() },
                    mediaScreenContent = { MediaScreen() },
                    settingsScreenContent = { SettingsScreen(){
                        viewModel.updateTheme(it)
                    } }
                )
            }
        }
    }
}