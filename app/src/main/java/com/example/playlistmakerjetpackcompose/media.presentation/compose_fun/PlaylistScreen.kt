package com.example.playlistmakerjetpackcompose.media.presentation.compose_fun

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.media.presentation.view_model.PlayListScreenViewModel
import com.example.playlistmakerjetpackcompose.settings.presentation.view_model.SettingsScreenViewModel
import com.example.playlistmakerjetpackcompose.ui.theme.YsMediumFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlaylistScreen(
    createOnClickContent: () -> Unit,
    viewModelTheme: SettingsScreenViewModel = koinViewModel(),
    viewModel: PlayListScreenViewModel = koinViewModel(),
    onClickItem: (Long) -> Unit
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
            onClick = { createOnClickContent() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
        ) {
            Text(
                text = stringResource(id = R.string.new_playlist),
                fontSize = 14.sp,
                fontFamily = YsMediumFamily,
                color = MaterialTheme.colorScheme.background
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (playLists.value.isNotEmpty()) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                playLists.value.forEach {
                    item {
                        PlayListItem(playList = it) {
                            onClickItem(it)
                        }
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
                text = stringResource(R.string.your_dont_create_playlist),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 19.sp,
                fontFamily = YsMediumFamily
            )
        }

    }
}

