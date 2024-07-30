package com.example.playlistmakerjetpackcompose.search.data.dto

class TrackResponse(
    val resultCount: Int,
    val results: List<TrackDto>
) : Response()