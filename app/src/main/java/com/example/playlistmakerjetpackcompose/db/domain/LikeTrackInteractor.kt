package com.example.playlistmakerjetpackcompose.db.domain

import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface LikeTrackInteractor {
    fun likeTrackList(): Flow<List<Track>>
    suspend fun addLikeTrack(track: Track)
    suspend fun deleteLikeTrack(track: Track)
}