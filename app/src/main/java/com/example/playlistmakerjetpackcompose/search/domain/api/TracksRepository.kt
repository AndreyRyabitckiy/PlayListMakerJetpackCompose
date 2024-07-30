package com.example.playlistmakerjetpackcompose.search.domain.api

import com.example.playlistmakerjetpackcompose.search.domain.models.TrackResults
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    fun searchTracks(expression: String): Flow<TrackResults>
}