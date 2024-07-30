package com.example.playlistmakerjetpackcompose.playlist_create.domain.models

data class PlayList (
    val id: Long = 0,
    val namePlayList: String,
    val aboutPlayList: String?,
    val roadToFileImage: String,
    val count: Int = 0,
)