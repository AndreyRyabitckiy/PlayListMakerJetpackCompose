package com.example.playlistmakerjetpackcompose.db.domain

import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface LikeTrackRepository {
    suspend fun addLikeTrack(track: Track)

    suspend fun deleteLikeTrack(track: Track)

    fun likeTrackList(): Flow<List<Track>>
}