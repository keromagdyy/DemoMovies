package com.cfh.demomovies.data.interfaces

import com.cfh.demomovies.utils.ConstantLinks
import com.cfh.demomovies.data.model.common.BaseModel
import com.cfh.demomovies.data.model.movie.MovieModel
import retrofit2.http.GET

interface IMovies {

    @GET(ConstantLinks.POPULAR)
    suspend fun getPopularMovies(): BaseModel<List<MovieModel>>

    @GET(ConstantLinks.UPCOMING)
    suspend fun getUpcomingMovies(): BaseModel<List<MovieModel>>
}