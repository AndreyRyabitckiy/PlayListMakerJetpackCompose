package com.example.playlistmakerjetpackcompose.db.domain

import com.example.playlistmakerjetpackcompose.playlist_create.domain.models.PlayList
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {
    suspend fun newPlayList(playList: PlayList): Long

    fun listPlayList(): Flow<List<PlayList>>

    suspend fun updatePlayList(track: Track, id: Long): Boolean

    suspend fun getCountTracks(id: Long): Int

    fun listTrackPlaylist(id: Long): Flow<List<Track>>

    suspend fun getTimesTracks(id: Long): String

    suspend fun deleteTrack(id: Long)

    suspend fun deletePlaylist(id: Long)

    suspend fun shareTracks(id: Long)

    suspend fun editPlayList(playList: PlayList)

    suspend fun getPlayList(id: Long): PlayList

}