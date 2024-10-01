package com.cfh.demomovies.data.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cfh.demomovies.data.model.common.BaseModel
import com.cfh.demomovies.data.model.db.PopularMoviesDBModel
import com.cfh.demomovies.data.model.movie.MovieModel

@Dao
interface PopularMoviesDao {

    @Query("SELECT * FROM popularMovies")
    suspend fun getAllPopularMovies(): PopularMoviesDBModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(customers: PopularMoviesDBModel)

    @Query("DELETE FROM popularMovies")
    suspend fun deletePopularMovies()
}