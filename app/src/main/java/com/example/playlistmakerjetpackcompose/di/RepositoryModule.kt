package com.example.playlistmaker.di


import com.example.playlistmakerjetpackcompose.db.data.LikeTrackRepositoryImpl
import com.example.playlistmakerjetpackcompose.db.data.PlayListRepositoryImpl
import com.example.playlistmakerjetpackcompose.db.data.converters.PlayListDbConvertor
import com.example.playlistmakerjetpackcompose.db.data.converters.TrackDbConvertor
import com.example.playlistmakerjetpackcompose.db.domain.LikeTrackRepository
import com.example.playlistmakerjetpackcompose.db.domain.PlayListRepository
import com.example.playlistmakerjetpackcompose.playlist_create.data.SaveImageToMemoryRepositoryImpl
import com.example.playlistmakerjetpackcompose.playlist_create.domain.SaveImageToMemoryRepository
import com.example.playlistmakerjetpackcompose.search.data.SharedPrefsRepositoryImpl
import com.example.playlistmakerjetpackcompose.search.data.TrackRepositoryImpl
import com.example.playlistmakerjetpackcompose.search.domain.api.TracksRepository
import com.example.playlistmakerjetpackcompose.search.domain.sharedprefs.SharedPrefsRepository
import com.example.playlistmakerjetpackcompose.settings.data.ExternalNavigatorImpl
import com.example.playlistmakerjetpackcompose.settings.data.SettingsRepositoryImpl
import com.example.playlistmakerjetpackcompose.settings.domain.ExternalNavigator
import com.example.playlistmakerjetpackcompose.settings.domain.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    single<SettingsRepository> {
        SettingsRepositoryImpl(context = androidContext())
    }

    single<LikeTrackRepository> {
        LikeTrackRepositoryImpl(get(), get())
    }

    single<SharedPrefsRepository> {
        SharedPrefsRepositoryImpl(storage = get(), likeTrackDatabase = get())
    }

    factory<ExternalNavigator> {
        ExternalNavigatorImpl(context = androidContext())
    }

    single<TracksRepository> {
        TrackRepositoryImpl(networkClient = get(), likeTrackDatabase = get())
    }

    single<SaveImageToMemoryRepository> {
        SaveImageToMemoryRepositoryImpl(get())
    }

    factory { TrackDbConvertor() }

    factory { PlayListDbConvertor() }

    single<PlayListRepository> {
        PlayListRepositoryImpl(get(), get(), androidContext())
    }

}