package com.example.playlistmakerjetpackcompose.media.presentation.compose_fun

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.media.presentation.view_model.LikeMusicViewModel
import com.example.playlistmakerjetpackcompose.search.presentation.compose_fun.ItemTrack
import com.example.playlistmakerjetpackcompose.settings.presentation.view_model.SettingsViewModel
import com.example.playlistmakerjetpackcompose.ui.theme.YsMediumFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun LikedTracksScreen(
    viewModel: LikeMusicViewModel = koinViewModel(),
    viewModelTheme: SettingsViewModel = koinViewModel()
                      ) {
    val trackList = viewModel.tracksLiked.observeAsState(listOf())
    viewModel.update()
    val theme = viewModelTheme.theme.observeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 28.dp)
    ) {
        if (trackList.value.isNotEmpty()) {
            LazyColumn {
                trackList.value.forEach { track ->
                    item {
                        ItemTrack(track = track)
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
                text = "Ваша медиатека пуста",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 19.sp,
                fontFamily = YsMediumFamily
            )
        }

    }
}
