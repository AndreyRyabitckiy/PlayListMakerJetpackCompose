package com.example.playlistmaker.di

import com.example.playlistmakerjetpackcompose.about_playlist.presentation.view_model.AboutPlayListFragmentViewModel
import com.example.playlistmakerjetpackcompose.media.presentation.view_model.LikeMusicViewModel
import com.example.playlistmakerjetpackcompose.media.presentation.view_model.PlayListViewModel
import com.example.playlistmakerjetpackcompose.player.presentation.view_model.MusicPlayerViewModel
import com.example.playlistmakerjetpackcompose.playlist_create.presentation.view_model.CreatePlayListFragmentViewModel
import com.example.playlistmakerjetpackcompose.search.domain.models.Track
import com.example.playlistmakerjetpackcompose.search.presentation.view_model.SearchFragmentViewModel
import com.example.playlistmakerjetpackcompose.settings.presentation.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel<MusicPlayerViewModel> { (track: Track) ->
        MusicPlayerViewModel(track, get(), get(), get())
    }

    viewModel<LikeMusicViewModel> {
        LikeMusicViewModel(get())
    }

    viewModel<SearchFragmentViewModel> {
        SearchFragmentViewModel(
            sharedPrefsInteractor = get(),
            tracksInteractor = get()
        )
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(
            sharingInteractor = get(),
            settingsInteractor = get()
        )
    }

    viewModel<PlayListViewModel> {
        PlayListViewModel(
            get()
        )
    }

    viewModel<CreatePlayListFragmentViewModel> {
        CreatePlayListFragmentViewModel(
            playListInteractor = get(),
            saveImageInteractor = get()
        )
    }

    viewModel<AboutPlayListFragmentViewModel>{
        AboutPlayListFragmentViewModel(
            get()
        )
    }
}