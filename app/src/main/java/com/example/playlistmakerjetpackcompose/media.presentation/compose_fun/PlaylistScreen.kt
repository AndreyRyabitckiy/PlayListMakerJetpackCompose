package com.example.playlistmakerjetpackcompose.media.presentation.compose_fun

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.media.presentation.view_model.PlayListViewModel
import com.example.playlistmakerjetpackcompose.navigation.Screen
import com.example.playlistmakerjetpackcompose.settings.presentation.view_model.SettingsViewModel
import com.example.playlistmakerjetpackcompose.ui.theme.YsMediumFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlaylistScreen(
    navHostController: NavHostController,
    viewModelTheme: SettingsViewModel = koinViewModel(),
    viewModel: PlayListViewModel = koinViewModel()
) {
    val playLists = viewModel.playLists.observeAsState(listOf())
    viewModel.update()

    val theme = viewModelTheme.theme.observeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 28.dp)
    ) {
        Button(
            onClick = {
                navHostController.navigate(Screen.CreateScreen.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
        ) {
            Text(text = "Новый плейлист", fontSize = 14.sp, fontFamily = YsMediumFamily)
        }
        if (playLists.value.isNotEmpty()) {

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                playLists.value.forEach{
                item {
                    PlayListItem(playList = it)
                }

                }
            }
        } else {
            Spacer(modifier = Modifier.height(80.dp))
            if (theme.value == true) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder_no_track_night),
                    contentDescription = null,
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.placeholder_no_track_day),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                textAlign = TextAlign.Center,
                text = "Вы не создали \nни одного плейлиста",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 19.sp,
                fontFamily = YsMediumFamily
            )
        }

    }
}

