package com.flutteryapps.moviemania.ui.main.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flutteryapps.moviemania.ui.main.view.fragment.MovieListFragment

/**
 * Created by umang on 11/06/20.
 */
class MoviesPagerAdapter(activity: AppCompatActivity, val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount() = itemsCount

    override fun createFragment(position: Int): Fragment {
        return MovieListFragment.getInstance(position)
    }
}