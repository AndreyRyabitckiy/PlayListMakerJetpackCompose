package com.example.playlistmakerjetpackcompose.media.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmakerjetpackcompose.db.domain.LikeTrackInteractor
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import kotlinx.coroutines.launch

class LikedTracksScreenViewModel(private val likeTrackInteractor: LikeTrackInteractor) : ViewModel() {
    private val _tracks = MutableLiveData<List<Track>>()
    val tracksLiked: LiveData<List<Track>>
        get() = _tracks

    fun update() {
        viewModelScope.launch {
            likeTrackInteractor.likeTrackList().collect {
                _tracks.postValue(it)
            }
        }
    }
}