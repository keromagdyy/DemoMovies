package com.cfh.demomovies

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.cfh.demomovies.data.model.movie.MovieModel
import com.cfh.demomovies.presentation.ui.movieDetails.ui.MovieDetailScreen
import org.junit.Rule
import org.junit.Test

class MovieDetailsActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMovieDetailScreen() {
        val movie = MovieModel(
            id = 123,
            title = "Test Movie",
            posterPath = "/path/to/poster.jpg",
            overview = "This is a test movie.",
            voteAverage = 8.5f,
            releaseDate = "2023-03-01"
        )

        composeTestRule.setContent {
            val context = LocalContext.current
            MovieDetailScreen(movieModel = movie, {})
        }

        composeTestRule.onNodeWithTag("title").assertTextEquals(movie.title)
        composeTestRule.onNodeWithTag("release_date").assertTextContains(movie.releaseDate)
        composeTestRule.onNodeWithTag("rating").assertTextEquals("${movie.voteAverage}")
        composeTestRule.onNodeWithTag("overview").assertTextEquals(movie.overview)
    }
}
