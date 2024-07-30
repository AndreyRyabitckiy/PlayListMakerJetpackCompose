package com.example.playlistmakerjetpackcompose.about_playlist.presentation.view_model

import com.example.playlistmakerjetpackcompose.search.domain.models.Track

data class AboutPlaylistState(
    val tracks: List<Track>,
    val time: String,
    val count: String,
)