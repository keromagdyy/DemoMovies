package com.cfh.demomovies.data.statuesValue.upcomingMovies

sealed class UpcomingMoviesIntent {

    data object UpcomingMovies : UpcomingMoviesIntent()
}