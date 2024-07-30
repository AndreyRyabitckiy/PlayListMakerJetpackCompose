package com.example.playlistmakerjetpackcompose.player.presentation.compose_fun

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.playlist_create.domain.models.PlayList
import com.example.playlistmakerjetpackcompose.ui.theme.YsRegularFamily

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayListBottomItem(
    playList: PlayList,
    onClick: (PlayList) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 12.dp)
            .fillMaxWidth()
            .clickable { onClick(playList) },
        verticalAlignment = Alignment.CenterVertically
    ) {

        GlideImage(
            contentScale = ContentScale.Crop,
            loading = placeholder(R.drawable.place_holder),
            failure = placeholder(R.drawable.place_holder),
            model = playList.roadToFileImage,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                maxLines = 1,
                text = playList.namePlayList,
                fontSize = 16.sp,
                fontFamily = YsRegularFamily,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                maxLines = 1,
                text = convert(playList.count),
                fontSize = 11.sp,
                fontFamily = YsRegularFamily,
                color = Color.Gray
            )
        }
    }
}

private fun convert(countTrack: Int): String {
    if (countTrack % 10 == 0) {
        return "$countTrack треков"
    }
    if (countTrack % 10 == 1 && !(countTrack % 100 >= 11 && countTrack % 100 <= 19)) {
        return "$countTrack трек"
    }
    if (countTrack % 10 < 5 && !(countTrack % 100 >= 11 && countTrack % 100 <= 19)) {
        return "$countTrack трека"
    } else {
        return "$countTrack треков"
    }
}