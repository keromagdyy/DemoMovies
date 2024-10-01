package com.cfh.demomovies.data.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cfh.demomovies.data.model.db.PopularMoviesDBModel
import com.cfh.demomovies.data.model.db.UpcomingMoviesDBModel

@Dao
interface UpcomingMoviesDao {

    @Query("SELECT * FROM upcomingMovies")
    suspend fun getAllUpcomingMovies(): UpcomingMoviesDBModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(customers: UpcomingMoviesDBModel)

    @Query("DELETE FROM upcomingMovies")
    suspend fun deleteUpcomingMovies()
}