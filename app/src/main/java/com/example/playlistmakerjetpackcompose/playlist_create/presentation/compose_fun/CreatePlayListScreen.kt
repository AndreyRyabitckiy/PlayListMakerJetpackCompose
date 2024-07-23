package com.example.playlistmakerjetpackcompose.playlist_create.presentation.compose_fun

import android.net.Uri
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.playlist_create.presentation.view_model.CreatePlayListFragmentViewModel
import com.example.playlistmakerjetpackcompose.ui.theme.PlayListMakerJetpackComposeTheme
import com.example.playlistmakerjetpackcompose.ui.theme.YsMediumFamily
import com.example.playlistmakerjetpackcompose.ui.theme.YsRegularFamily
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun PreviewLight() {
    PlayListMakerJetpackComposeTheme(
        darkTheme = false
    ) {
        CreatePlayListScreen()
    }
}

@Preview
@Composable
fun PreviewNight() {
    PlayListMakerJetpackComposeTheme(
        darkTheme = true
    ) {
        CreatePlayListScreen()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreatePlayListScreen(viewModel:CreatePlayListFragmentViewModel = koinViewModel()) {

    var uri = remember { mutableStateOf<Uri?>(null) }
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_back_to_past_screen
                ),
                contentDescription = null,
                modifier = Modifier.padding(20.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Новый плейлист",
                fontSize = 22.sp,
                fontFamily = YsMediumFamily,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        if (uri.value == null) {
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .clickable {

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
                            color = Color.Gray,
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
                        .clickable {
                            singlePhotoPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
        } else {
            GlideImage(

                contentScale = ContentScale.Crop,
                loading = placeholder(R.drawable.place_holder),
                failure = placeholder(R.drawable.place_holder),
                model = uri.value,
                contentDescription = null,
                modifier = Modifier
                    .padding(24.dp)
                    .clickable {

                    }
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }


        Spacer(modifier = Modifier.height(32.dp))
        val message = remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = message.value,
            onValueChange = {newText->
                message.value = newText
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp),
            label = {
                Text(
                    text = "Название*",
                    fontSize = 16.sp,
                    fontFamily = YsRegularFamily,
                    color = colorResource(id = R.color.grey_playlist)
                )
            },
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(16.dp))

        val message1 = remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = message1.value,
            onValueChange = {newText ->
                message1.value = newText
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp),
            label = {
                Text(
                    text = "Описание",
                    fontSize = 16.sp,
                    fontFamily = YsRegularFamily,
                    color = colorResource(id = R.color.grey_playlist)
                )
            }
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.yp_blue)),
                onClick = { viewModel.createNewPlayList(message.value, message1.value, uri.value) },
            ) {

                Text(
                    text = "Создать",
                    fontSize = 16.sp,
                    fontFamily = YsMediumFamily,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }
}
