package com.cfh.demomovies.presentation.ui.moviesList.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cfh.demomovies.data.statuesValue.popularMovies.PopularMoviesIntent
import com.cfh.demomovies.data.statuesValue.popularMovies.PopularMoviesStatus
import com.cfh.demomovies.data.statuesValue.upcomingMovies.UpcomingMoviesIntent
import com.cfh.demomovies.data.statuesValue.upcomingMovies.UpcomingMoviesStatus
import com.cfh.demomovies.domain.MoviesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MoviesViewModel(private val context: Context) : ViewModel() {

    val popularMoviesIntent = Channel<PopularMoviesIntent>(Channel.UNLIMITED)
    val upcomingMoviesIntent = Channel<UpcomingMoviesIntent>(Channel.UNLIMITED)

    private val _statePopular =
        MutableStateFlow<PopularMoviesStatus>(PopularMoviesStatus.Idle)
    private val _stateUpcoming =
        MutableStateFlow<UpcomingMoviesStatus>(UpcomingMoviesStatus.Idle)

    val statePopular: StateFlow<PopularMoviesStatus> get() = _statePopular
    val stateUpcoming: StateFlow<UpcomingMoviesStatus> get() = _stateUpcoming

    init {
        getPopularMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            popularMoviesIntent.consumeAsFlow().collect {
                when (it) {
                    is PopularMoviesIntent.PopularMovies -> observePopularMovies()
                }
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            upcomingMoviesIntent.consumeAsFlow().collect {
                when (it) {
                    is UpcomingMoviesIntent.UpcomingMovies -> observeUpcomingMovies()
                }
            }
        }
    }


    private fun observePopularMovies() {
        viewModelScope.launch {
            _statePopular.value = PopularMoviesStatus.Loading
            _statePopular.value = try {
                val response = MoviesRepository(context).getPopularMovies()
                PopularMoviesStatus.GetPopularMovies(response)
            } catch (e: Exception) {
                PopularMoviesStatus.Error(e.message)
            }
        }
    }

    private fun observeUpcomingMovies() {
        viewModelScope.launch {
            _stateUpcoming.value = UpcomingMoviesStatus.Loading
            _stateUpcoming.value = try {
                val response = MoviesRepository(context).getUpcomingMovies()
                UpcomingMoviesStatus.UpcomingMovies(response)
            } catch (e: Exception) {
                UpcomingMoviesStatus.Error(e.message)
            }
        }
    }


}