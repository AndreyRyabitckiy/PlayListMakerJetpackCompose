package com.example.playlistmakerjetpackcompose.playlist_create.presentation.compose_fun

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.playlist_create.domain.models.PlayList
import com.example.playlistmakerjetpackcompose.playlist_create.presentation.view_model.CreatePlayListScreenViewModel
import com.example.playlistmakerjetpackcompose.ui.theme.YsMediumFamily
import com.example.playlistmakerjetpackcompose.ui.theme.YsRegularFamily
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreatePlayListScreen(
    viewModel: CreatePlayListScreenViewModel = koinViewModel(),
    playlist: PlayList?,
    onClickBack: () -> Unit,
    onClickSave: (String) -> Unit
) {
    val messageAbout = remember {
        mutableStateOf(playlist?.aboutPlayList ?: "")
    }
    val messageName = remember {
        mutableStateOf(playlist?.namePlayList ?: "")
    }
    val colorName = remember {
        if (messageName.value.isNotEmpty()) {
            mutableIntStateOf(R.color.yp_blue)
        } else {
            mutableIntStateOf(R.color.grey_playlist)
        }
    }
    val colorAbout = remember {
        if (messageAbout.value.isNotEmpty()) {
            mutableIntStateOf(R.color.yp_blue)
        } else {
            mutableIntStateOf(R.color.grey_playlist)
        }
    }
    val enabledButton = remember {
        if (messageName.value.isNotEmpty()) {
            mutableStateOf(true)
        } else {
            mutableStateOf(false)
        }
    }
    val uri = remember { mutableStateOf(playlist?.roadToFileImage?.toUri()) }
    val openDialog = remember { mutableStateOf(false) }
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri.value = it
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {


        Spacer(modifier = Modifier.height(20.dp))
        if (uri.value == null) {
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .clickable {
                        singlePhotoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .drawWithContent {
                        val stroke = Stroke(
                            cap = StrokeCap.Square,
                            width = 1.dp.toPx(),
                            pathEffect =
                            PathEffect.dashPathEffect(
                                floatArrayOf(100f, 50f), 10f
                            )
                        )
                        drawOutline(
                            outline = Outline.Rectangle(
                                Rect(
                                    Offset(x = 10f, y = 0f),
                                    Size(width = size.width - 15, height = size.width - 10)
                                )
                            ),
                            color = Color(0xFFAEAFB4),
                            style = stroke

                        )
                        drawContent()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add_playlist),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(24.dp)
                )
            }
        } else {
            GlideImage(
                contentScale = ContentScale.Crop,
                loading = placeholder(R.drawable.place_holder),
                failure = placeholder(R.drawable.place_holder),
                model = uri.value.toString(),
                contentDescription = null,
                modifier = Modifier
                    .padding(24.dp)
                    .clickable {
                        singlePhotoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            singleLine = true,
            value = messageName.value,
            onValueChange = { newText ->
                messageName.value = newText
                if (messageName.value == "") {
                    colorName.intValue = R.color.grey_playlist
                    enabledButton.value = false
                } else {
                    colorName.intValue = R.color.yp_blue
                    enabledButton.value = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp),
            label = {
                Text(
                    text = stringResource(R.string.name_playlist),
                    fontSize = 16.sp,
                    fontFamily = YsRegularFamily,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                cursorColor = colorResource(id = R.color.yp_blue),
                focusedIndicatorColor = colorResource(id = colorName.intValue),
                unfocusedIndicatorColor = colorResource(id = colorName.intValue),
            )

        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            singleLine = true,
            value = messageAbout.value,
            onValueChange = { newText ->
                messageAbout.value = newText
                if (messageAbout.value == "") {
                    colorAbout.intValue = R.color.grey_playlist
                } else {
                    colorAbout.intValue = R.color.yp_blue
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp),
            label = {
                Text(
                    text = stringResource(R.string.about),
                    fontSize = 16.sp,
                    fontFamily = YsRegularFamily,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }, colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                cursorColor = colorResource(id = R.color.yp_blue),
                focusedIndicatorColor = colorResource(id = colorAbout.intValue),
                unfocusedIndicatorColor = colorResource(id = colorAbout.intValue),
            )
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                enabled = enabledButton.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    disabledContainerColor = colorResource(id = R.color.grey_playlist)
                ),
                onClick = {
                    if (playlist == null) {
                        viewModel.createNewPlayList(
                            messageName.value,
                            messageAbout.value,
                            uri.value
                        )
                        onClickSave("Плейлист «${messageName.value}» создан")
                        onClickBack()
                    } else {
                        viewModel.editNewPlayList(
                            playlist.roadToFileImage.toUri(),
                            playlist.id,
                            messageName.value,
                            messageAbout.value,
                            uri.value
                        )
                        onClickBack()
                    }
                },
            ) {
                if (playlist != null) {
                    Text(
                        text = stringResource(id = R.string.update),
                        fontSize = 16.sp,
                        fontFamily = YsMediumFamily,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    Text(
                        text = stringResource(R.string.create),
                        fontSize = 16.sp,
                        fontFamily = YsMediumFamily,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = { Text(text = stringResource(R.string.canel_save)) },
                    text = {
                        Text(
                            stringResource(R.string.no_save)
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { onClickBack() })
                        { Text(stringResource(R.string.completed)) }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            openDialog.value = false
                        }) { Text(stringResource(R.string.cancel)) }
                    }
                )
            }
        }
    }

    BackHandler {
        if (playlist == null) {
            if (messageName.value != "" || messageAbout.value != "" || uri.value != null) {
                openDialog.value = true
            } else {
                onClickBack()
            }
        } else {
            onClickBack()
        }
    }
}