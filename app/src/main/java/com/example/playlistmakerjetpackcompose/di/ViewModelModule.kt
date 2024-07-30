package com.example.playlistmakerjetpackcompose.di

import com.example.playlistmakerjetpackcompose.about_playlist.presentation.view_model.AboutPlayListScreenViewModel
import com.example.playlistmakerjetpackcompose.media.presentation.view_model.LikedTracksScreenViewModel
import com.example.playlistmakerjetpackcompose.media.presentation.view_model.PlayListScreenViewModel
import com.example.playlistmakerjetpackcompose.player.presentation.view_model.PlayerScreenViewModel
import com.example.playlistmakerjetpackcompose.playlist_create.presentation.view_model.CreatePlayListScreenViewModel
import com.example.playlistmakerjetpackcompose.search.presentation.view_model.SearchScreenViewModel
import com.example.playlistmakerjetpackcompose.settings.presentation.view_model.SettingsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel<PlayerScreenViewModel> {
        PlayerScreenViewModel(get(), get(), get())
    }

    viewModel<LikedTracksScreenViewModel> {
        LikedTracksScreenViewModel(get())
    }

    viewModel<SearchScreenViewModel> {
        SearchScreenViewModel(
            sharedPrefsInteractor = get(),
            tracksInteractor = get()
        )
    }

    viewModel<SettingsScreenViewModel> {
        SettingsScreenViewModel(
            sharingInteractor = get(),
            settingsInteractor = get()
        )
    }

    viewModel<PlayListScreenViewModel> {
        PlayListScreenViewModel(
            get()
        )
    }

    viewModel<CreatePlayListScreenViewModel> {
        CreatePlayListScreenViewModel(
            playListInteractor = get(),
            saveImageInteractor = get()
        )
    }

    viewModel<AboutPlayListScreenViewModel>{
        AboutPlayListScreenViewModel(
            get()
        )
    }
}