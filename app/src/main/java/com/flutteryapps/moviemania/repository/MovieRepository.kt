package com.flutteryapps.moviemania.repository

import com.flutteryapps.moviemania.network.AllMoviesResponse
import com.flutteryapps.moviemania.network.ApiClientService
import com.flutteryapps.moviemania.network.GetAllGenresResponse
import kotlinx.coroutines.Deferred
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieRepository : KoinComponent {

    private val apiClientService: ApiClientService by inject()

    fun getGenresOfMoviesAsync(): Deferred<GetAllGenresResponse> {
        return apiClientService.getGenresOfMoviesAsync()
    }

    fun getPopularMoviesAsync(page: Int): Deferred<AllMoviesResponse> {
        return apiClientService.getPopularMoviesAsync(page)
    }

    fun getPopularTVSeasonsAsync(page: Int): Deferred<AllMoviesResponse> {
        return apiClientService.getPopularTVSeasonsAsync(page)
    }
}