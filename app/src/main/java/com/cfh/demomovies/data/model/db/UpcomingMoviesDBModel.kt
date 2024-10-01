package com.cfh.demomovies.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cfh.demomovies.data.db.room.converter.MoviesConverter
import com.cfh.demomovies.data.model.movie.MovieModel
import java.io.Serializable

@Entity(tableName = "upcomingMovies")
@TypeConverters(MoviesConverter::class)
data class UpcomingMoviesDBModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @TypeConverters(MoviesConverter::class)
    val upcoming: List<MovieModel>,
): Serializable