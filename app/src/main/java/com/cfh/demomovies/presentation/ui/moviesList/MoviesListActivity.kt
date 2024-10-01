package com.cfh.demomovies.presentation.ui.moviesList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.cfh.demomovies.data.statuesValue.popularMovies.PopularMoviesIntent
import com.cfh.demomovies.data.statuesValue.upcomingMovies.UpcomingMoviesIntent
import com.cfh.demomovies.presentation.ui.moviesList.viewmodel.MoviesViewModel
import com.cfh.demomovies.presentation.ui.moviesList.viewmodel.MoviesViewModelFactory
import com.cfh.demomovies.presentation.ui.moviesList.ui.MovieListScreen
import kotlinx.coroutines.launch

class MoviesListActivity : ComponentActivity() {
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            MoviesViewModelFactory(this@MoviesListActivity)
        )[MoviesViewModel::class.java]

        getPopularAndUpcomingMovies()

        setContent {
            MovieListScreen(this@MoviesListActivity, viewModel = viewModel)
        }
    }

    private fun getPopularAndUpcomingMovies() {
        lifecycleScope.launch {
            viewModel.popularMoviesIntent.send(PopularMoviesIntent.PopularMovies)
            viewModel.upcomingMoviesIntent.send(UpcomingMoviesIntent.UpcomingMovies)
        }
    }
}