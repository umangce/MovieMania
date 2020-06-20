package com.flutteryapps.moviemania.network

import com.google.gson.annotations.SerializedName

/**
 * Created by umang on 08/06/20.
 */

class GetAllGenresResponse {
    val genres: List<Genre>? = null
}

class Genre {
    val id: Int? = null
    val name: String? = null
}

class AllMoviesResponse {
    var page: Int? = null
    var totalResults: Int? = null
    var totalPages: Int? = null
    var results: List<Movie>? = null
}

class Movie {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("adult")
    var adult: Boolean? = null

    @SerializedName("popularity")
    var popularity: Double? = null

    @SerializedName("vote_count")
    var voteCount: Int? = null

    @SerializedName("video")
    var video: Boolean? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("backdrop_path")
    var backdropPath: String? = null

    @SerializedName("original_language")
    var originalLanguage: String? = null

    @SerializedName("original_title")
    var originalTitle: String? = null

    @SerializedName("genre_ids")
    var genreIds: List<Int>? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("vote_average")
    var voteAverage: Double? = null

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("release_date")
    var releaseDate: String? = null
}