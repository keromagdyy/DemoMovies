package com.cfh.demomovies.data.statuesValue.popularMovies

sealed class PopularMoviesIntent {

    data object PopularMovies : PopularMoviesIntent()
}