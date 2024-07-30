package com.example.playlistmakerjetpackcompose.search.presentation.compose_fun

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.search.domain.models.Track


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemTrack(
    track: Track,
    clickOnItem: (Track) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 8.dp)
            .clickable {
                clickOnItem(track)
            }
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        GlideImage(
            loading = placeholder(R.drawable.place_holder),
            failure = placeholder(R.drawable.place_holder),
            model = track.artworkUrl100,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(6.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                maxLines = 1,
                text = track.trackName,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    maxLines = 1,
                    text = track.artistName,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_ellipse),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(4.dp),
                )
                Text(
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    maxLines = 1,
                    text = track.trackTimeMillis,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary,
                )

            }
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(end = 20.dp)
        )


    }
}
