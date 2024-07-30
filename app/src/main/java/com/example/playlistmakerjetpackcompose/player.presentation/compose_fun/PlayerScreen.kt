package com.example.playlistmakerjetpackcompose.player.presentation.compose_fun

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.player.presentation.view_model.PlayerScreenViewModel
import com.example.playlistmakerjetpackcompose.player.presentation.view_model.PlayerState
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import com.example.playlistmakerjetpackcompose.ui.theme.YsMediumFamily
import com.example.playlistmakerjetpackcompose.ui.theme.YsRegularFamily
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    track: Track,
    viewModel: PlayerScreenViewModel = koinViewModel(),
    toastMessage: (String) -> Unit,
    onBackScreen: () -> Unit,
    onClickCreatePlayList: () -> Unit

) {

    LaunchedEffect(key1 = Unit) {
        viewModel.trackAddedFlow.collect {
            if (!it.answer) {
                toastMessage("Трек уже добавлен в плейлист «${it.name}»")
            } else {
                toastMessage("Добавлено в плейлист «${it.name}»")
            }
        }
    }
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }
    viewModel.update()
    val playLists = viewModel.playLists.observeAsState(listOf())
    viewModel.getTrack(track)
    val playerState = viewModel.playerState.observeAsState()
    val timerTrack = viewModel.timerLiveData.observeAsState()
    val isLiked = viewModel.isLiked.observeAsState()
    val icLike = remember {
        mutableIntStateOf(R.drawable.ic_like_no)
    }
    val colorLike = remember {
        mutableStateOf(Color.White)
    }
    if (isLiked.value == true) {
        icLike.intValue = R.drawable.ic_like_yes
        colorLike.value = colorResource(id = R.color.red)
    } else {
        icLike.intValue = R.drawable.ic_like_no
        colorLike.value = Color.White
    }
    val playIc = remember {
        mutableIntStateOf(R.drawable.ic_play)
    }

    if (playerState.value == PlayerState.PLAYING) {
        playIc.intValue = R.drawable.ic_pause
    } else {
        playIc.intValue = R.drawable.ic_play
    }
    val url = track.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg")
    viewModel.preparePlayer()
    viewModel.getLikeStatus(track.trackId)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Icon(
            tint = MaterialTheme.colorScheme.onBackground,
            painter = painterResource(id = R.drawable.ic_back_to_past_screen),
            contentDescription = null,
            modifier = Modifier
                .padding(20.dp)
                .clickable {
                    viewModel.pausePlayer()
                    onBackScreen()
                    viewModel.mediaPlayerRelease()
                }
        )
        GlideImage(
            contentScale = ContentScale.Crop,
            loading = placeholder(R.drawable.place_holder),
            failure = placeholder(R.drawable.place_holder),
            model = url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .padding(26.dp)
        )

        Text(
            text = track.trackName,
            fontFamily = YsMediumFamily,
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Text(
            text = track.artistName,
            fontFamily = YsMediumFamily,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .clickable { showBottomSheet.value = true }
                    .size(50.dp)
                    .background(color = MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_playlist_player),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .clickable {
                        viewModel.playbackControl()
                        viewModel.startTimerMusic()
                        viewModel.setPlayerPosition()
                    }
                    .size(84.dp)
                    .background(color = MaterialTheme.colorScheme.onBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = playIc.intValue),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }


            Box(
                modifier = Modifier
                    .clickable { viewModel.changeLikedStatus() }
                    .clip(RoundedCornerShape(100))
                    .size(50.dp)
                    .background(color = MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icLike.intValue),
                    contentDescription = null,
                    tint = colorLike.value
                )
            }

        }

        Text(
            textAlign = TextAlign.Center,
            text = timerTrack.value ?: "0:30",
            fontFamily = YsMediumFamily,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))

        ItemPlayer(
            nameRow = stringResource(R.string.time_long),
            dataNameRow = track.trackTimeMillis
        )
        if (track.collectionName != null) {
            ItemPlayer(
                nameRow = stringResource(R.string.collection_name),
                dataNameRow = track.collectionName.toString()
            )
        }
        ItemPlayer(
            nameRow = stringResource(R.string.year),
            dataNameRow = track.releaseDate.toString()
        )
        ItemPlayer(
            nameRow = stringResource(R.string.gendre_name),
            dataNameRow = track.primaryGenreName.toString()
        )
        ItemPlayer(
            nameRow = stringResource(R.string.country),
            dataNameRow = track.country.toString()
        )
        Spacer(modifier = Modifier.height(20.dp))


        if (showBottomSheet.value) {
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxHeight(0.5f),
                onDismissRequest = {
                    showBottomSheet.value = false
                },
                sheetState = sheetState
            ) {
                Text(
                    stringResource(R.string.add_to_playlist),
                    fontFamily = YsMediumFamily,
                    fontSize = 19.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { onClickCreatePlayList() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
                    ) {
                        Text(
                            text = stringResource(R.string.new_playlist),
                            fontSize = 14.sp,
                            fontFamily = YsMediumFamily,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
                LazyColumn {
                    playLists.value.forEach { playList ->
                        item {
                            PlayListBottomItem(playList = playList) { playlist ->
                                viewModel.insertInPlayList(playlist.id, playlist.namePlayList)
                                showBottomSheet.value = false
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ItemPlayer(nameRow: String, dataNameRow: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = nameRow,
            fontSize = 13.sp,
            fontFamily = YsRegularFamily,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = dataNameRow,
            fontSize = 13.sp,
            fontFamily = YsRegularFamily,
            color = MaterialTheme.colorScheme.onBackground
        )

    }

}
