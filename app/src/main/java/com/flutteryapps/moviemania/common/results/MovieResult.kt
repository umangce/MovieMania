package com.flutteryapps.moviemania.common.results

import com.flutteryapps.moviemania.network.Movie

/**
 * Created by umang on 13/06/20.
 */
sealed class MovieResult {
    object Loading : MovieResult()

    object LoadingMore : MovieResult()

    object InternetNotAvailable : MovieResult()

    data class Success(val movies: List<Movie>) : MovieResult()

    data class Failure(val message: String?) : MovieResult()
}