package com.cfh.demomovies.presentation.ui.movieDetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cfh.demomovies.R
import com.cfh.demomovies.data.model.movie.MovieModel
import com.cfh.demomovies.presentation.ui.theme.gray20
import com.cfh.demomovies.presentation.ui.theme.gray40
import com.cfh.demomovies.presentation.ui.theme.gray80
import com.cfh.demomovies.presentation.ui.theme.orange
import com.cfh.demomovies.presentation.ui.theme.white
import java.io.File


@Composable
fun MovieDetailScreen(movieModel: MovieModel, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = gray80)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Box {
            AsyncImage(
                model = File(movieModel.posterPath),
                contentDescription = movieModel.title,
                modifier = Modifier
                    .fillMaxSize()
                    .height(450.dp),
                contentScale = ContentScale.FillBounds
            )
            AsyncImage(
                model = R.drawable.view,
                contentDescription = movieModel.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.FillBounds
            )
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(50.dp)
                    .padding(top = 16.dp, start = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = white // Adjust this color as needed
                )
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = movieModel.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = white
                ),
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Release Date:",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = gray40
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = movieModel.releaseDate,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = white
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rating: ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = gray20,
                        fontSize = 16.sp
                    )
                )
                StarRating(rating = movieModel.voteAverage)
                val rate = String.format("%.1f", movieModel.voteAverage).toDouble()
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "$rate",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = gray20,
                        fontSize = 16.sp
                    )
                )

            }

            Text(
                text = movieModel.overview,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    color = gray40
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun StarRating(
    rating: Float,
    modifier: Modifier = Modifier,
) {
    val filledStars = maxOf((rating / 10 * 5).toInt(), 0)
    val halfStars = if ((rating % 10 >= 5)) 1 else 0
    val emptyStars = 5 - filledStars - halfStars

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = orange,
                modifier = Modifier.size(24.dp)
            )
        }
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = gray40,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    MovieDetailScreen(movieModel = MovieModel(), {  })
}