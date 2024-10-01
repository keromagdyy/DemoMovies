package com.cfh.demomovies.data.db.room.converter

import androidx.room.TypeConverter
import com.cfh.demomovies.data.model.movie.MovieModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MoviesConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromCustomersListToString(results: List<MovieModel>?): String? {
        return gson.toJson(results)
    }

    @TypeConverter
    fun fromStringToCustomersList(string: String?): List<MovieModel>? {
        val type = object : TypeToken<List<MovieModel>>() {}.type
        return gson.fromJson(string, type)
    }
}
