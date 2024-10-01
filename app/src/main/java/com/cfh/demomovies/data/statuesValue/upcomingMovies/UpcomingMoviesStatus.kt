package com.cfh.demomovies.data.statuesValue.upcomingMovies

import com.cfh.demomovies.data.model.common.BaseModel
import com.cfh.demomovies.data.model.movie.MovieModel

sealed class UpcomingMoviesStatus {

    data object Idle : UpcomingMoviesStatus()
    data object Loading : UpcomingMoviesStatus()
    data class UpcomingMovies(val data: List<MovieModel>) : UpcomingMoviesStatus()
    data class Error(val error: String?) : UpcomingMoviesStatus()

}