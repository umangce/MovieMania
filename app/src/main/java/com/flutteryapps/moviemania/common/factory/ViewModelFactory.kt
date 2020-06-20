package com.flutteryapps.moviemania.common.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flutteryapps.moviemania.ui.main.viewmodel.MovieListViewModel

/**
 * Created by umang on 04/03/19.
 */
class ViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}