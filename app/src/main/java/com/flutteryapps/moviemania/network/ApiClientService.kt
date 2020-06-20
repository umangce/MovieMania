package com.flutteryapps.moviemania.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by umang on 08/06/20.
 */
interface ApiClientService {

    @GET("/3/genre/movie/list")
    fun getGenresOfMoviesAsync(): Deferred<GetAllGenresResponse>

    @GET("/4/discover/movie")
    fun getMoviesRunningInTheatres(
        @Query("primary_release_date.gte") minDate: String,  // YYYY-MM-DD
        @Query("primary_release_date.lte") maxDate: String   // YYYY-MM-DD
    )

    @GET("/4/discover/movie?sort_by=popularity.desc")
    fun getPopularMoviesAsync(@Query("page") pageId: Int): Deferred<AllMoviesResponse>

    @GET("/3/discover/tv?sort_by=popularity.desc")
    fun getPopularTVSeasonsAsync(@Query("page") pageId: Int): Deferred<AllMoviesResponse>


    @GET("/3/movie/{movieId}?language=en-US&sort_by=popularity.desc")
    fun getMovieDetails(@Path("movieId") movieId: Int)
}