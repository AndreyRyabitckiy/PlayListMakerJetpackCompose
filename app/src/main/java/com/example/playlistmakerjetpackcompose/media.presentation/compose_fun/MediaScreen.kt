package com.example.playlistmakerjetpackcompose.media.presentation.compose_fun

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import com.example.playlistmakerjetpackcompose.ui.theme.YsMediumFamily

@Composable
fun MediaScreen(
    createOnClickContent: () -> Unit,
    onClickItem: (Long) -> Unit,
    onClickTrack: (Track) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val tabIndex = rememberSaveable {
            mutableIntStateOf(0)
        }
        val tabs = listOf(stringResource(R.string.liked_track), stringResource(R.string.playlist))

        Text(
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = YsMediumFamily,
            fontSize = 22.sp,
            text = stringResource(id = R.string.media),
            modifier = Modifier
                .padding(16.dp)
        )
        TabRow(
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex.intValue]),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            selectedTabIndex = tabIndex.intValue,
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title, color = MaterialTheme.colorScheme.onBackground) },
                    selected = tabIndex.intValue == index,
                    onClick = { tabIndex.intValue = index }
                )
            }
        }
        when (tabIndex.intValue) {
            0 -> LikedTracksScreen(clickItem = { onClickTrack(it) })
            1 -> PlaylistScreen(
                createOnClickContent = { createOnClickContent() },
                onClickItem = { onClickItem(it) })
        }
    }
}