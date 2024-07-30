package com.example.playlistmakerjetpackcompose.search.data

import com.example.playlistmakerjetpackcompose.search.data.dto.TrackDto
import com.example.playlistmakerjetpackcompose.search.domain.models.Track

interface SearchHistoryStorage {
    fun read(): ArrayList<TrackDto>

    fun write(track: Track?): ArrayList<TrackDto>

    fun clearHistory(): ArrayList<TrackDto>
}