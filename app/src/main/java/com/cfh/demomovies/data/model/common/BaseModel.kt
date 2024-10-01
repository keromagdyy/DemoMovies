package com.cfh.demomovies.data.model.common

import com.google.gson.annotations.SerializedName

data class BaseModel<T>(
    val page: Int = 0,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0,
    var results: T?,
)