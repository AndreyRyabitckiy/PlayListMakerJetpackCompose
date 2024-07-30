package com.example.playlistmakerjetpackcompose.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.settings.presentation.compose_fun.SettingsScreen
import com.example.playlistmakerjetpackcompose.settings.presentation.view_model.SettingsScreenViewModel
import com.example.playlistmakerjetpackcompose.ui.theme.PlayListMakerJetpackComposeTheme
import com.example.playlistmakerjetpackcompose.ui.theme.YsRegularFamily
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigatorPlayListMaker(viewModel: SettingsScreenViewModel = koinViewModel()) {

    val viewModelTheme = viewModel.theme.observeAsState(false)
    PlayListMakerJetpackComposeTheme(darkTheme = viewModelTheme.value) {
        val navHostController = rememberNavController()
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val scope = rememberCoroutineScope()
        val dp = remember {
            mutableIntStateOf(55)
        }
        val snackBarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState) { data ->
                    Snackbar(
                        contentColor = MaterialTheme.colorScheme.background,
                        backgroundColor = MaterialTheme.colorScheme.onBackground,
                        snackbarData = data
                    )
                }
            },
            bottomBar = {
                if (currentRoute == Screen.SearchScreen.route || currentRoute == Screen.MediaScreen.route || currentRoute == Screen.SettingsScreen.route)
                    BottomNavigation(
                        backgroundColor = MaterialTheme.colorScheme.background
                    ) {
                        val items = listOf(
                            NavigationItem.Search,
                            NavigationItem.Media,
                            NavigationItem.Settings
                        )
                        items.forEachIndexed { index, item ->
                            BottomNavigationItem(
                                label = {
                                    if (currentRoute == item.screen.route) {
                                        Text(
                                            fontSize = 12.sp,
                                            text = stringResource(id = item.titleResId),
                                            fontFamily = YsRegularFamily,
                                            color = colorResource(id = R.color.yp_blue)
                                        )
                                    } else {
                                        Text(
                                            fontSize = 12.sp,
                                            text = stringResource(id = item.titleResId),
                                            fontFamily = YsRegularFamily,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                },
                                selected = currentRoute == item.screen.route,
                                onClick = {
                                    navHostController.navigate(item.screen.route) {
                                        launchSingleTop = true
                                        if (item.screen.route == Screen.ROTE_SEARCH_SCREEN) {
                                            restoreState
                                        }
                                        popUpTo(item.screen.route) {
                                            inclusive = false
                                            saveState = true
                                        }
                                    }
                                },
                                icon = {
                                    if (currentRoute == item.screen.route) {
                                        Icon(
                                            modifier = Modifier.padding(bottom = 4.dp),
                                            painter = painterResource(id = item.icon),
                                            contentDescription = null,
                                            tint = colorResource(id = R.color.yp_blue)
                                        )
                                    } else {
                                        Icon(
                                            modifier = Modifier.padding(bottom = 4.dp),
                                            painter = painterResource(id = item.icon),
                                            contentDescription = null,
                                        )
                                    }
                                },
                            )
                        }
                    }
            }
        ) { paddingValues ->
            paddingValues
            if (currentRoute == Screen.SearchScreen.route || currentRoute == Screen.MediaScreen.route || currentRoute == Screen.SettingsScreen.route) {
                dp.intValue = 55
            } else {
                dp.intValue = 0
            }
            Surface(modifier = Modifier.padding(bottom = dp.intValue.dp)) {
                AppNavGraph(
                    navHostController = navHostController,
                    createScreenContent = {
                        navHostController.navigate(
                            Screen.CreateScreen.getRouteWithArgs(
                                it
                            )
                        )
                    },
                    onClickBack = { navHostController.popBackStack() },
                    onClickSnackBar = { message ->
                        scope.launch {
                            snackBarHostState.showSnackbar(message)
                        }
                    },
                    onClickItem = {
                        navHostController.navigate(Screen.AboutPlayListScreen.getRouteWithArgs(id = it.toString()))
                    },
                    settingsScreenContent = {
                        SettingsScreen {
                            viewModel.updateTheme(it)
                        }
                    },
                    clickOnItemTrack = {
                        navHostController.navigate(Screen.PlayerScreen.getRouteWithArgs(track = it))
                    }
                )
            }
        }
    }
}