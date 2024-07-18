package com.example.playlistmakerjetpackcompose.search.domain.sharedprefs

import com.example.playlistmakerjetpackcompose.search.domain.models.Track

interface SharedPrefsInteractor {
    suspend fun readWriteClear(
        use: String,
        track: Track?,
        consumer: SharedPrefsConsumer
    )

    suspend fun readWriteClearWithoutConsumer(
        use: String,
        track: Track?
    ): List<Track>

    interface SharedPrefsConsumer {
        fun consume(foundSharedPrefs: ArrayList<Track>)
    }
}