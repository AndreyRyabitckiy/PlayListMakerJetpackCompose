package com.example.playlistmakerjetpackcompose.di


import com.example.playlistmakerjetpackcompose.db.domain.LikeTrackInteractor
import com.example.playlistmakerjetpackcompose.db.domain.PlayListInteractor
import com.example.playlistmakerjetpackcompose.db.domain.impl.LikeTrackInteractorImpl
import com.example.playlistmakerjetpackcompose.db.domain.impl.PlayListInteractorImpl
import com.example.playlistmakerjetpackcompose.playlist_create.domain.SaveImageToMemoryInteractor
import com.example.playlistmakerjetpackcompose.playlist_create.domain.impl.SaveImageToMemoryInteractorImpl
import com.example.playlistmakerjetpackcompose.search.domain.api.TracksInteractor
import com.example.playlistmakerjetpackcompose.search.domain.impl.SharedPrefsInteractorImpl
import com.example.playlistmakerjetpackcompose.search.domain.impl.TracksInteractorImpl
import com.example.playlistmakerjetpackcompose.search.domain.sharedprefs.SharedPrefsInteractor
import com.example.playlistmakerjetpackcompose.settings.domain.SettingsInteractor
import com.example.playlistmakerjetpackcompose.settings.domain.SharingInteractor
import com.example.playlistmakerjetpackcompose.settings.domain.impl.SettingsInteractorImpl
import com.example.playlistmakerjetpackcompose.settings.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val interatorModule = module {

    factory<SettingsInteractor> {
        SettingsInteractorImpl(repository = get())
    }

    factory<SharedPrefsInteractor> {
        SharedPrefsInteractorImpl(repository = get())
    }

    factory<SharingInteractor> {
        SharingInteractorImpl(externalNavigator = get(), settingsRepository = get())
    }

    factory<TracksInteractor> {
        TracksInteractorImpl(repository = get())
    }

    factory<LikeTrackInteractor> {
        LikeTrackInteractorImpl(get())
    }

    factory<PlayListInteractor> {
        PlayListInteractorImpl(get())
    }

    factory<SaveImageToMemoryInteractor> {
        SaveImageToMemoryInteractorImpl(get())
    }
}