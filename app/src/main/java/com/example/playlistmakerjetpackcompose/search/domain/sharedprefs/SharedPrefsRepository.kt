package com.example.playlistmakerjetpackcompose.search.domain.sharedprefs

import com.example.playlistmakerjetpackcompose.search.domain.models.Track

interface SharedPrefsRepository {
    suspend fun saveReadClear(
        use: String,
        track: Track?
    ): ArrayList<Track>
}