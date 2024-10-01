package com.cfh.demomovies.data.model.movie

import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cfh.demomovies.data.db.room.converter.MoviesConverter
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@TypeConverters(MoviesConverter::class)
data class MovieModel(
    @PrimaryKey val id: Long = 0,
    val adult: Boolean = false,
    @SerializedName("backdrop_path") val backdropPath: String = "",
    @SerializedName("poster_path") val posterPath: String = "",
    val title: String = "",
    val overview: String = "",
    @SerializedName("vote_average") val voteAverage: Float = 0.0f,
    @SerializedName("release_date") val releaseDate: String = "",
): Serializable