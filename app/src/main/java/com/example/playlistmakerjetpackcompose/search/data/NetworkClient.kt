package com.example.playlistmakerjetpackcompose.search.data

import com.example.playlistmakerjetpackcompose.search.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}