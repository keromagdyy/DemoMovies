package com.cfh.demomovies.domain

import android.content.Context
import com.cfh.demomovies.data.db.room.MoviesDatabase
import com.cfh.demomovies.data.interfaces.IMovies
import com.cfh.demomovies.data.model.db.PopularMoviesDBModel
import com.cfh.demomovies.data.model.db.UpcomingMoviesDBModel
import com.cfh.demomovies.data.model.movie.MovieModel
import com.cfh.demomovies.utils.CheckConnection
import com.cfh.demomovies.utils.ConstantLinks
import com.cfh.demomovies.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class MoviesRepository(val context: Context) {
    private val retrofit = RetrofitClient.getInstance(IMovies::class.java)
    private val distributionDatabase = MoviesDatabase

    private suspend fun getPopularMoviesFromDB() = distributionDatabase.getDatabase(context).getPopularMoviesDao().getAllPopularMovies()
    private suspend fun savePopularMoviesToDB(popularMoviesDBModel: PopularMoviesDBModel) = distributionDatabase.getDatabase(context).getPopularMoviesDao().insertAll(popularMoviesDBModel)
    private suspend fun deletePopularMoviesFromDB() = distributionDatabase.getDatabase(context).getPopularMoviesDao().deletePopularMovies()
    private suspend fun getPopularMoviesFromRemote() = retrofit.getPopularMovies()

    private suspend fun getUpcomingMoviesFromDB() = distributionDatabase.getDatabase(context).getUpcomingMoviesDao().getAllUpcomingMovies()
    private suspend fun saveUpcomingMoviesToDB(upcomingMoviesDBModel: UpcomingMoviesDBModel) = distributionDatabase.getDatabase(context).getUpcomingMoviesDao().insertAll(upcomingMoviesDBModel)
    private suspend fun deleteUpcomingMoviesFromDB() = distributionDatabase.getDatabase(context).getUpcomingMoviesDao().deleteUpcomingMovies()
    private suspend fun getUpcomingMoviesFromRemote() = retrofit.getUpcomingMovies()


    suspend fun getPopularMovies(): List<MovieModel> {
        if (CheckConnection.isInternetAvailable()) {
            val data = getPopularMoviesFromRemote()

            val updatedMovies = data.results?.map { movie ->
                val localImagePath = downloadAndSaveImage("${ConstantLinks.IMAGE_URL}${movie.posterPath}" ?: "", movie.id)
                movie.copy(posterPath = localImagePath!!)
            } ?: listOf()

            deletePopularMoviesFromDB()
            savePopularMoviesToDB(PopularMoviesDBModel(popular = updatedMovies))
        }
        return getPopularMoviesFromDB().popular
    }

    suspend fun getUpcomingMovies(): List<MovieModel> {
        if (CheckConnection.isInternetAvailable()) {
            val data = getUpcomingMoviesFromRemote()

            val updatedMovies = data.results?.map { movie ->
                val localImagePath = downloadAndSaveImage("${ConstantLinks.IMAGE_URL}${movie.posterPath}" ?: "", movie.id)
                movie.copy(posterPath = localImagePath!!)
            } ?: listOf()

            deleteUpcomingMoviesFromDB()
            saveUpcomingMoviesToDB(UpcomingMoviesDBModel(upcoming = updatedMovies))
        }
        return getUpcomingMoviesFromDB().upcoming
    }

    private suspend fun downloadAndSaveImage(imageUrl: String, movieId: Long): String? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()

                val inputStream = connection.inputStream
                val file = File(context.filesDir, "image_$movieId.jpg")
                val outputStream = FileOutputStream(file)

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                file.absolutePath // Return the local path of the saved image
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

}