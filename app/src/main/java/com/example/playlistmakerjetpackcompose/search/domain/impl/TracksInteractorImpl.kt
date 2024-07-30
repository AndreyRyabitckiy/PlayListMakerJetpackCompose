package com.example.playlistmakerjetpackcompose.search.domain.impl

import com.example.playlistmakerjetpackcompose.search.domain.api.TracksInteractor
import com.example.playlistmakerjetpackcompose.search.domain.api.TracksRepository
import com.example.playlistmakerjetpackcompose.search.domain.models.ResponseStatus
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksInteractorImpl(private val repository: TracksRepository) : TracksInteractor {

    override fun searchTracks(expression: String): Flow<Pair<List<Track>, ResponseStatus>> {

        return repository.searchTracks(expression).map { result ->
            Pair(result.data, result.status)
        }
    }
}