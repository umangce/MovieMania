package com.flutteryapps.moviemania.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.flutteryapps.moviemania.common.results.MovieResult
import com.flutteryapps.moviemania.common.utils.isInternetAvailable
import com.flutteryapps.moviemania.network.AllMoviesResponse
import com.flutteryapps.moviemania.network.Movie
import com.flutteryapps.moviemania.repository.MovieRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by umang on 08/06/20.
 */
class MovieListViewModel(private val applicationObject: Application) :
    AndroidViewModel(applicationObject), KoinComponent {

    var type = 1

    private var page = 1
    private val movieRepository: MovieRepository by inject()

    private val currentPageLiveData = MutableLiveData<Int>()
    private val movieListLiveData = currentPageLiveData.switchMap { page ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            if (isInternetAvailable(applicationObject)) {
                if (page == 1) {
                    emit(MovieResult.Loading)
                } else {
                    emit(MovieResult.LoadingMore)
                }
                try {
                    val genresDeferred = getDataFromServerAsync(page)
                    val movieListFromNetwork: List<Movie>? = genresDeferred.await().results
                    if (movieListFromNetwork != null && movieListFromNetwork.isNotEmpty()) {
                        emit(MovieResult.Success(movieListFromNetwork))
                    }
                } catch (e: Exception) {
                    emit(MovieResult.Failure(e.message))
                }
            } else {
                emit(MovieResult.InternetNotAvailable)
            }
        }
    }

    val getMovieList: LiveData<MovieResult>
        get() = movieListLiveData

    internal fun initializeData(type: Int) {
        this.type = type

        currentPageLiveData.value = page
    }

    fun nextPage() {
        if (!(movieListLiveData.value == MovieResult.Loading
                    || movieListLiveData.value == MovieResult.LoadingMore)
        ) {
            page++
            currentPageLiveData.value = page
        }
    }

    private fun getDataFromServerAsync(page: Int): Deferred<AllMoviesResponse> {
        return if (type == 0) {
            movieRepository.getPopularMoviesAsync(page)
        } else {
            movieRepository.getPopularTVSeasonsAsync(page)
        }
    }

    companion object {
        const val TAG = "MovieListViewModel"
    }
}