package com.example.playlistmakerjetpackcompose.media.presentation.compose_fun

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun PlayListItem(
    playList: PlayList,
    onClickItem: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClickItem(playList.id) }
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .fillMaxSize()
    ) {
        GlideImage(
            contentScale = ContentScale.Crop,
            loading = placeholder(R.drawable.place_holder),
            failure = placeholder(R.drawable.place_holder),
            model = playList.roadToFileImage,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .aspectRatio(1f)
        )
        Text(
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            maxLines = 1,
            text = playList.namePlayList,
            fontFamily = YsRegularFamily,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            maxLines = 1,
            text = convert(playList.count),
            fontFamily = YsRegularFamily,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

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