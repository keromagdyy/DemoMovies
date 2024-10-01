package com.cfh.demomovies.presentation.ui.movieDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cfh.demomovies.data.model.movie.MovieModel
import com.cfh.demomovies.presentation.ui.movieDetails.ui.MovieDetailScreen

class MovieDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movie = intent.getSerializableExtra("movie") as MovieModel
        setContent {
            MovieDetailScreen(movieModel = movie, onBackClick = {
                finish()
            })
        }
    }
}
