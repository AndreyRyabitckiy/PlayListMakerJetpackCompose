package com.example.playlistmakerjetpackcompose.playlist_create.data

import com.example.playlistmakerjetpackcompose.playlist_create.domain.SaveImageToMemoryRepository

class SaveImageToMemoryRepositoryImpl(private val saveImageToMemory: SaveImageToMemory) :
    SaveImageToMemoryRepository {
    override suspend fun saveImageToFile(uri: String): String {
        return saveImageToMemory.saveImageToFile(uri)
    }
}