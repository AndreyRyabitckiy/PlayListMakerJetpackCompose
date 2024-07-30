package com.example.playlistmakerjetpackcompose.playlist_create.domain

interface SaveImageToMemoryRepository {
    suspend fun saveImageToFile(uri: String): String
}