package com.example.playlistmakerjetpackcompose.search.domain.models

data class TrackResults(
    val status: ResponseStatus,
    val data: List<Track> = emptyList()
)


enum class ResponseStatus {
    SUCCESS,
    LOADING,
    EMPTY,
    ERROR
}