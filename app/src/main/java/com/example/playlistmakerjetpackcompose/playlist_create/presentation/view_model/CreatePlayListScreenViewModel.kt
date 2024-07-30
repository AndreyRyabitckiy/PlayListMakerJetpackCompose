package com.example.playlistmakerjetpackcompose.playlist_create.presentation.view_model

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmakerjetpackcompose.db.domain.PlayListInteractor
import com.example.playlistmakerjetpackcompose.playlist_create.domain.SaveImageToMemoryInteractor
import com.example.playlistmakerjetpackcompose.playlist_create.domain.models.PlayList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CreatePlayListScreenViewModel(
    private val playListInteractor: PlayListInteractor,
    private val saveImageInteractor: SaveImageToMemoryInteractor
) : ViewModel() {

    fun createNewPlayList(name: String, about: String, road: Uri?) {
        viewModelScope.launch {
            val id = playListInteractor.newPlayList(
                PlayList(
                    namePlayList = name,
                    aboutPlayList = about,
                    roadToFileImage = saveImageToFile(road)
                )
            )
        }
    }

    fun editNewPlayList(oldRoad: Uri?, id: Long, name: String, about: String, road: Uri?) {
        viewModelScope.launch {
            playListInteractor.editPlayList(
                PlayList(
                    id = id,
                    namePlayList = name,
                    aboutPlayList = about,
                    roadToFileImage = oldOrNewUri(oldRoad, road)
                )
            )
        }
    }

    private fun oldOrNewUri(oldRoad: Uri?, road: Uri?): String {
        if (oldRoad == road) {
            return oldRoad.toString()
        } else {
            return saveImageToFile(road)
        }
    }

    private val _nameImage = MutableLiveData<String>()
    val nameImage: LiveData<String>
        get() = _nameImage

    fun saveImageToFile(uri: Uri?): String {
        var road: String = ""
        if (uri != null) {
            runBlocking { road = saveImageInteractor.saveImageToFile(uri.toString()) }
        }
        return road
    }
}