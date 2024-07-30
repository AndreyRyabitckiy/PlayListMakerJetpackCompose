@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.playlistmakerjetpackcompose.search.presentation.compose_fun

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.search.domain.models.ResponseStatus
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import com.example.playlistmakerjetpackcompose.search.presentation.view_model.SearchScreenViewModel
import com.example.playlistmakerjetpackcompose.settings.presentation.view_model.SettingsScreenViewModel
import com.example.playlistmakerjetpackcompose.ui.theme.YsMediumFamily
import com.example.playlistmakerjetpackcompose.ui.theme.YsRegularFamily
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = koinViewModel(),
    viewModelTheme: SettingsScreenViewModel = koinViewModel(),
    clickOnItem: (Track) -> Unit
) {
    val theme = viewModelTheme.theme.observeAsState()
    val message = rememberSaveable {
        mutableStateOf("")
    }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val searchStatus = viewModel.searchStatus.observeAsState()
    val trackList = viewModel.tracks.observeAsState(listOf())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = YsMediumFamily,
            fontSize = 22.sp,
            text = stringResource(R.string.search),
            modifier = Modifier
                .padding(16.dp)
        )

        TextField(
            keyboardActions = KeyboardActions(
                onDone = {
                    keyBoardController?.hide()
                    viewModel.clearTrackList()
                    if (message.value != "") {
                        viewModel.setSearchText(message.value)
                        viewModel.searchTracks(message.value)
                    }
                }
            ),
            trailingIcon = {
                if (message.value != "") {
                    Icon(
                        tint = colorResource(id = R.color.black),
                        painter = painterResource(id = R.drawable.ic_clear),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            message.value = ""
                        }
                    )
                }
            },
            singleLine = true,
            value = message.value,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = colorResource(id = R.color.grey_playlist)
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.search),
                    fontSize = 16.sp,
                    fontFamily = YsRegularFamily,
                    color = colorResource(id = R.color.grey_playlist)
                )
            },
            onValueChange = { newText ->
                viewModel.clearTrackList()
                message.value = newText
                if (newText != "") {
                    viewModel.setSearchText(newText)
                    viewModel.searchDebounce()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .onFocusChanged {
                    if (it.isFocused) {
                        viewModel.searchTracks("")
                        viewModel.showHistoryBoolean(true)
                    }
                },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(id = R.color.yp_light_grey),
                cursorColor = colorResource(id = R.color.yp_blue),
                focusedTextColor = Color.Black,
            )
        )

        when (searchStatus.value) {
            ResponseStatus.SUCCESS -> {
                if (trackList.value.isNotEmpty()) {
                    if (message.value != "") {
                        Spacer(modifier = Modifier.height(24.dp))
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            trackList.value.forEach {
                                item {
                                    ItemTrack(it) {
                                        clickOnItem(it)
                                    }
                                }
                            }
                        }
                    } else {
                        ShowHistory(
                            viewModel = viewModel,
                            trackList = trackList.value,
                            message = message.value,
                            clickOnItem = { clickOnItem(it) })
                    }
                }
            }

            ResponseStatus.LOADING -> {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.yp_blue),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }

            ResponseStatus.EMPTY -> {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (message.value == "") {
                        ShowHistory(
                            viewModel = viewModel,
                            trackList = trackList.value,
                            message = message.value,
                            clickOnItem = { clickOnItem(it) })

                    } else {
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
                            text = stringResource(R.string.nothing_search),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 19.sp,
                            fontFamily = YsMediumFamily
                        )
                    }
                }
            }

            ResponseStatus.ERROR -> {
                if (message.value == "") {
                    ShowHistory(
                        viewModel = viewModel,
                        trackList = trackList.value,
                        message = message.value,
                        clickOnItem = { clickOnItem(it) })
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(top = 100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isSystemInDarkTheme()) {
                            Image(
                                painter = painterResource(id = R.drawable.placeholder_no_inter_dark),
                                contentDescription = null,
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.placeholder_no_inter_day),
                                contentDescription = null,
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(R.string.error_internet),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 19.sp,
                            fontFamily = YsMediumFamily
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { viewModel.searchTracks(message.value) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
                        ) {
                            Text(
                                text = stringResource(R.string.update),
                                fontSize = 14.sp,
                                fontFamily = YsMediumFamily
                            )
                        }
                    }
                }
            }

            null -> {}
        }
    }
}

@Composable
fun ShowHistory(
    viewModel: SearchScreenViewModel,
    clickOnItem: (Track) -> Unit,
    message: String,
    trackList: List<Track>
) {
    viewModel.showHistoryBoolean(true)
    Spacer(modifier = Modifier.height(24.dp))
    if (message == "") {
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = stringResource(R.string.you_search),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = YsMediumFamily,
            fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            trackList.forEach {
                item {
                    ItemTrack(it) {
                        viewModel.writeHistory(it)
                        clickOnItem(it)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(34.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { viewModel.clearHistory() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
                    ) {
                        Text(
                            text = stringResource(R.string.clear_history),
                            fontSize = 14.sp,
                            fontFamily = YsMediumFamily,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        }
    }
}

