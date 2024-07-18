package com.example.playlistmakerjetpackcompose.search.domain.api

import com.example.playlistmakerjetpackcompose.search.domain.models.ResponseStatus
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksInteractor {
    fun searchTracks(expression: String): Flow<Pair<List<Track>, ResponseStatus>>
}