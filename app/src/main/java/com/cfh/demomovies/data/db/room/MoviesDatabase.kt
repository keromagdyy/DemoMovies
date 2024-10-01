package com.cfh.demomovies.data.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cfh.demomovies.data.db.room.converter.MoviesConverter
import com.cfh.demomovies.data.db.room.dao.PopularMoviesDao
import com.cfh.demomovies.data.db.room.dao.UpcomingMoviesDao
import com.cfh.demomovies.data.model.db.PopularMoviesDBModel
import com.cfh.demomovies.data.model.db.UpcomingMoviesDBModel
import com.cfh.demomovies.data.model.movie.MovieModel

@Database(
    entities = [PopularMoviesDBModel::class, UpcomingMoviesDBModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MoviesConverter::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getPopularMoviesDao(): PopularMoviesDao
    abstract fun getUpcomingMoviesDao(): UpcomingMoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MoviesDatabase::class.java,
                    "movies_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}