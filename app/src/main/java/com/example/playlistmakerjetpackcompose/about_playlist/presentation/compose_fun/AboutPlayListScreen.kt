package com.example.playlistmakerjetpackcompose.about_playlist.presentation.compose_fun

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FixedThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.about_playlist.presentation.view_model.AboutPlayListScreenViewModel
import com.example.playlistmakerjetpackcompose.player.presentation.compose_fun.PlayListBottomItem
import com.example.playlistmakerjetpackcompose.playlist_create.domain.models.PlayList
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import com.example.playlistmakerjetpackcompose.settings.presentation.view_model.SettingsScreenViewModel
import com.example.playlistmakerjetpackcompose.ui.theme.YsBoldFamily
import com.example.playlistmakerjetpackcompose.ui.theme.YsMediumFamily
import com.example.playlistmakerjetpackcompose.ui.theme.YsRegularFamily
import org.koin.androidx.compose.koinViewModel

@OptIn(
    ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun AboutPlayList(
    viewModel: AboutPlayListScreenViewModel = koinViewModel(),
    id: String,
    onClickBack: () -> Unit,
    onClickSnackBar: (String) -> Unit,
    onClickEditPlayList: (PlayList) -> Unit,
    clickOnItem: (Track) -> Unit,
    viewModelTheme: SettingsScreenViewModel = koinViewModel()
) {
    viewModel.update(id = id.toLong())
    viewModel.updatePlayList(id = id.toLong())


    val theme = viewModelTheme.theme.observeAsState()
    val deleteTrack = remember {
        mutableStateOf<Track?>(null)
    }

    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }
    val aboutPlayList = viewModel.aboutPlayListState.observeAsState()
    val playlist = viewModel.playList.observeAsState()
    val lazyListSate = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.yp_light_grey))
    ) {
        val openDialogDelete = remember {
            mutableStateOf(false)
        }
        val openDialog = remember { mutableStateOf(false) }
        Box {
            GlideImage(
                contentScale = ContentScale.Crop,
                loading = placeholder(R.drawable.place_holder),
                failure = placeholder(R.drawable.place_holder),
                model = playlist.value?.roadToFileImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)

            )
            Icon(
                painter = painterResource(id = R.drawable.ic_back_to_past_screen),
                contentDescription = null,
                modifier = Modifier
                    .padding(20.dp)
                    .clickable { onClickBack() }
            )
        }
        Text(
            color = colorResource(id = R.color.black),
            text = playlist.value?.namePlayList.toString(),
            fontSize = 24.sp,
            fontFamily = YsBoldFamily,
            modifier = Modifier.padding(start = 16.dp, top = 24.dp)
        )
        Text(
            color = colorResource(id = R.color.black),
            text = playlist.value?.aboutPlayList.toString(),
            fontSize = 18.sp,
            fontFamily = YsRegularFamily,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                color = colorResource(id = R.color.black),
                text = aboutPlayList.value?.time ?: "",
                fontSize = 18.sp,
                fontFamily = YsRegularFamily,
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_elipse_playlist),
                contentDescription = null,
                tint = colorResource(id = R.color.black),
                modifier = Modifier.padding(horizontal = 5.dp)
            )

            Text(
                color = colorResource(id = R.color.black),
                text = aboutPlayList.value?.count ?: "",
                fontSize = 18.sp,
                fontFamily = YsRegularFamily,
            )
        }

        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.clickable {
                    if (aboutPlayList.value?.tracks?.isNotEmpty() == true) {
                        viewModel.shareTracks(playlist.value?.id ?: 0L)
                    } else {
                        onClickSnackBar("В этом плейлисте нет списка треков, которым можно поделиться")
                    }
                }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_3_elipse),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 26.dp)
                    .clickable { showBottomSheet.value = true }
            )

        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (aboutPlayList.value?.tracks?.isNotEmpty() == true) {

                LazyColumn(state = lazyListSate, modifier = Modifier.padding(12.dp)) {
                    aboutPlayList.value?.tracks?.forEach {
                        item(key = it.id) {
                            val dismissState = rememberDismissState()
                            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                                viewModel.deleteTrack(it.id!!, playlist.value!!.id)
                            }
                            SwipeToDismiss(state = dismissState, background = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    Text(
                                        text = stringResource(R.string.delete),
                                        fontSize = 24.sp,
                                        color = Color.Red,
                                        fontFamily = YsBoldFamily,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_delete),
                                        contentDescription = null,
                                        modifier = Modifier.padding(end = 15.dp)
                                    )
                                }
                            },
                                directions = setOf(DismissDirection.EndToStart),
                                dismissThresholds = {
                                    FixedThreshold(150.dp)
                                }
                            ) {

                                ItemTrackBottom(
                                    onLongClick = {
                                        deleteTrack.value = it
                                        openDialogDelete.value = true
                                    },
                                    clickOnItem = { clickOnItem(it) },
                                    track = it
                                )
                            }

                        }
                    }
                }
            } else {
                if (theme.value == true) {
                    Image(
                        modifier = Modifier.padding(top = 12.dp),
                        painter = painterResource(id = R.drawable.placeholder_no_track_night),
                        contentDescription = null,
                    )
                } else {
                    Image(
                        modifier = Modifier.padding(top = 12.dp),
                        painter = painterResource(id = R.drawable.placeholder_no_track_day),
                        contentDescription = null,
                    )
                }
                Text(
                    text = stringResource(R.string.nulll_media),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 19.sp,
                    fontFamily = YsMediumFamily
                )
            }
        }

        if (showBottomSheet.value) {
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxHeight(0.5f),
                onDismissRequest = {
                    showBottomSheet.value = false
                },
                sheetState = sheetState
            ) {
                playlist.value?.let {
                    PlayListBottomItem(playList = it) {}
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.share),
                        fontFamily = YsRegularFamily,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 20.dp)
                            .clickable {
                                if (aboutPlayList.value?.tracks?.isNotEmpty() == true) {
                                    viewModel.shareTracks(playlist.value?.id ?: 0L)
                                } else {
                                    onClickSnackBar("В этом плейлисте нет списка треков, которым можно поделиться")
                                }
                            }
                    )
                    Text(
                        text = stringResource(R.string.edit_information),
                        fontFamily = YsRegularFamily,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 20.dp)
                            .clickable { onClickEditPlayList(playlist.value!!) }
                    )
                    Text(
                        text = stringResource(R.string.delete_playlist),
                        fontFamily = YsRegularFamily,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 20.dp)
                            .clickable {
                                showBottomSheet.value = false
                                openDialog.value = true
                            }
                    )

                }

            }
        }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(
                        text = "Хотите удалить плейлист «${playlist.value?.namePlayList}»?",
                        fontSize = 16.sp
                    )
                },

                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deletePlaylist()
                        onClickBack()
                    })
                    { Text("Да") }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog.value = false }) { Text("Нет") }
                }
            )
        }

        if (openDialogDelete.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialogDelete.value = false
                },
                title = { Text(text = "Хотите удалить трект «${playlist.value?.namePlayList}»?") },

                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteTrack(deleteTrack.value?.id!!, playlist.value!!.id)
                        openDialogDelete.value = false
                    })
                    { Text("Да") }
                },
                dismissButton = {
                    TextButton(onClick = { openDialogDelete.value = false }) { Text("Нет") }
                }
            )
        }
    }

}

