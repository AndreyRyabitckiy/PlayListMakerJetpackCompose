package com.example.playlistmakerjetpackcompose.playlist_create.domain

interface SaveImageToMemoryInteractor {
    suspend fun saveImageToFile(uri: String): String
}