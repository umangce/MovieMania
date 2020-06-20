package com.flutteryapps.moviemania.di

import com.flutteryapps.moviemania.network.client.HttpClient
import com.flutteryapps.moviemania.repository.MovieRepository
import org.koin.dsl.module

/**
 * Created by umang on 08/06/20.
 */

val applicationModule = module(override = true) {
    single { HttpClient().getApiClientService() }
    factory { MovieRepository() }
}