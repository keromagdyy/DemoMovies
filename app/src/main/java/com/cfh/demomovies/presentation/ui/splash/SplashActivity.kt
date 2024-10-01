package com.cfh.demomovies.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cfh.demomovies.presentation.ui.moviesList.MoviesListActivity
import com.cfh.demomovies.presentation.ui.splash.ui.SplashScreen

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashScreen {
                startActivity(Intent(this, MoviesListActivity::class.java))
                finish()
            }

        }
    }
}