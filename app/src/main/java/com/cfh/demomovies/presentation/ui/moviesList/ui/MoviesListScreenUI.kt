package com.cfh.demomovies.presentation.ui.moviesList.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cfh.demomovies.presentation.ui.movieDetails.MovieDetailsActivity
import com.cfh.demomovies.R
import com.cfh.demomovies.data.model.movie.MovieModel
import com.cfh.demomovies.data.statuesValue.popularMovies.PopularMoviesStatus
import com.cfh.demomovies.data.statuesValue.upcomingMovies.UpcomingMoviesStatus
import com.cfh.demomovies.presentation.ui.moviesList.viewmodel.MoviesViewModel
import com.cfh.demomovies.presentation.ui.commonUi.EmptyContent
import com.cfh.demomovies.presentation.ui.commonUi.ErrorContent
import com.cfh.demomovies.presentation.ui.commonUi.LoadingIndicator
import com.cfh.demomovies.presentation.ui.theme.gray40
import com.cfh.demomovies.presentation.ui.theme.gray60
import com.cfh.demomovies.presentation.ui.theme.gray80
import com.cfh.demomovies.presentation.ui.theme.white
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun MovieListScreen(context: Context?, viewModel: MoviesViewModel?) {
    val popularMovies by viewModel!!.statePopular.collectAsState()
    val upcomingMovies by viewModel!!.stateUpcoming.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = gray80)
    ) {
        SearchView(searchQuery = searchQuery, onSearchChanged = { searchQuery = it })

        when (upcomingMovies) {
            is UpcomingMoviesStatus.UpcomingMovies -> {
                MovieSlider(
                    context!!,
                    (upcomingMovies as UpcomingMoviesStatus.UpcomingMovies).data ?: emptyList()
                )
            }

            is UpcomingMoviesStatus.Loading -> {
                LoadingIndicator()
            }

            is UpcomingMoviesStatus.Idle -> {
                EmptyContent()
            }

            is UpcomingMoviesStatus.Error -> {
                ErrorContent((upcomingMovies as UpcomingMoviesStatus.Error).error.toString())
            }
        }
        when (popularMovies) {
            is PopularMoviesStatus.GetPopularMovies -> {
                val filteredMovies =
                    (popularMovies as PopularMoviesStatus.GetPopularMovies).data?.filter {
                        it.title.lowercase().contains(searchQuery.lowercase())
                    } ?: emptyList()

                if (filteredMovies.isEmpty()) {
                    EmptyContent()
                } else {
                    MovieLazyColumn(context!!, filteredMovies)
                }
            }

            is PopularMoviesStatus.Loading -> {
                LoadingIndicator()
            }

            is PopularMoviesStatus.Idle -> {
                EmptyContent()
            }

            is PopularMoviesStatus.Error -> {
                ErrorContent((popularMovies as PopularMoviesStatus.Error).error.toString())
            }
        }


    }
}

@Composable
fun SearchView(searchQuery: String, onSearchChanged: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChanged,
            shape = RoundedCornerShape(35),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = { Text(stringResource(R.string.search_popular_movies)) },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.search))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = gray40,
                unfocusedContainerColor = gray40,
                focusedTextColor = white,
                focusedBorderColor = gray40,
                unfocusedBorderColor = gray40,
                focusedLabelColor = gray40,
                cursorColor = white
            ),
            singleLine = true
        )
    }
}

@Composable
fun MovieSlider(context: Context?, movieModels: List<MovieModel>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % movieModels.size
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = gray80)
    ) {
        Text(
            text = stringResource(R.string.upcoming_movies),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            fontSize = 16.sp,
            color = white
        )

        HorizontalPager(
            count = movieModels.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(horizontal = 8.dp)
        ) { page ->
            MovieSliderCard(movieModel = movieModels[page], onClick = { movie ->
                val intent = Intent(context, MovieDetailsActivity::class.java)
                intent.putExtra("movie", movieModels[page])
                context!!.startActivity(intent)
            })
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            inactiveColor = gray60,
            activeColor = white,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun MovieLazyColumn(context: Context, movieModels: List<MovieModel>) {

    Column {
        Text(
            text = stringResource(R.string.popular_movies),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            fontSize = 16.sp,
            color = white
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(movieModels) { movie ->
                MovieCard(movieModel = movie, onClick = { movieModel ->
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra("movie", movieModel)
                    context.startActivity(intent)
                })
            }
        }
    }
}

@Composable
fun MovieSliderCard(movieModel: MovieModel, onClick: (MovieModel) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = white),
                onClick = { onClick(movieModel) },
            ),

        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(disabledElevation = 8.dp)
    ) {
        Box {
            AsyncImage(
                model = File(movieModel.posterPath),
                contentDescription = movieModel.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            AsyncImage(
                model = R.drawable.view,
                contentDescription = movieModel.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = movieModel.title,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                fontSize = 16.sp,
                color = white
            )

        }
    }
}

@Composable
fun MovieCard(movieModel: MovieModel, onClick: (MovieModel) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = { onClick(movieModel) },
            ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = gray60,
        ),
        elevation = CardDefaults.cardElevation(disabledElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = File(movieModel.posterPath),
                contentDescription = movieModel.title,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RectangleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = movieModel.title,
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp,
                color = white
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListScreenPreview() {
    MovieListScreen(context = null, viewModel = null)
}