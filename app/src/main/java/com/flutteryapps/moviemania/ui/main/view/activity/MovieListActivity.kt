package com.flutteryapps.moviemania.ui.main.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flutteryapps.moviemania.R
import com.flutteryapps.moviemania.databinding.ActivityMainBinding
import com.flutteryapps.moviemania.ui.main.adapter.MoviesPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val headers = arrayOf("Movies", "TV Seasons");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.collapsingToolbarLayout.isTitleEnabled = false
        title = getString(R.string.app_name)
        setupViewPager()
    }

    private fun setupViewPager() {

        val adapter = MoviesPagerAdapter(this, headers.size)
        binding.viewPagerMovies.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPagerMovies) { tab, position ->
            tab.text = headers[position]
        }.attach()
    }

    companion object {
        const val TAG = "MovieListActivity"
    }
}
