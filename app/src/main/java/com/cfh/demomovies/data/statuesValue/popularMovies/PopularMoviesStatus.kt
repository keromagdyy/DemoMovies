package com.cfh.demomovies.data.statuesValue.popularMovies

import com.cfh.demomovies.data.model.common.BaseModel
import com.cfh.demomovies.data.model.movie.MovieModel

sealed class PopularMoviesStatus {

    data object Idle : PopularMoviesStatus()
    data object Loading : PopularMoviesStatus()
    data class GetPopularMovies(val data: List<MovieModel>) : PopularMoviesStatus()
    data class Error(val error: String?) : PopularMoviesStatus()

}