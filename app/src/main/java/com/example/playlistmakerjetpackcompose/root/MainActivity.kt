package com.example.playlistmakerjetpackcompose.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.playlistmakerjetpackcompose.navigation.NavigatorPlayListMaker
import com.example.playlistmakerjetpackcompose.playlist_create.presentation.compose_fun.CreatePlayListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigatorPlayListMaker()
        }
    }
}