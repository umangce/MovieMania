package com.flutteryapps.moviemania.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flutteryapps.moviemania.common.factory.ViewModelFactory
import com.flutteryapps.moviemania.common.results.MovieResult
import com.flutteryapps.moviemania.common.utils.gone
import com.flutteryapps.moviemania.common.utils.visible
import com.flutteryapps.moviemania.databinding.FragmentMovieListBinding
import com.flutteryapps.moviemania.ui.main.adapter.MoviesAdapter
import com.flutteryapps.moviemania.ui.main.viewmodel.MovieListViewModel
import com.google.android.material.snackbar.Snackbar

class MovieListFragment : Fragment() {

    private lateinit var adapter: MoviesAdapter
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var movieListViewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListViewModel =
            ViewModelProvider(this, ViewModelFactory(requireActivity().application))
                .get(MovieListViewModel::class.java)

        val type = arguments?.getInt(ARGS_TYPE) ?: 1
        movieListViewModel.initializeData(type)

        binding.recyclerViewMovies.setHasFixedSize(true)
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewMovies.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        binding.recyclerViewMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    movieListViewModel.nextPage()
                }
            }
        })

        adapter = MoviesAdapter(requireContext())
        binding.recyclerViewMovies.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieListViewModel.getMovieList.observe(requireActivity(), movieObserver)
    }

    private val movieObserver = Observer { movieResult: MovieResult ->

        when (movieResult) {
            is MovieResult.Loading -> {
                binding.shimmerViewContainer.startShimmer()
                binding.shimmerViewContainer.visible()
            }

            is MovieResult.InternetNotAvailable -> {
                val message = "No Internet"
                Snackbar.make(binding.recyclerViewMovies, message, Snackbar.LENGTH_LONG).show()
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.gone()
            }

            is MovieResult.Failure -> {
                val message = movieResult.message ?: "Something went wrong."
                Snackbar.make(binding.recyclerViewMovies, message, Snackbar.LENGTH_LONG).show()

                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.gone()
            }

            is MovieResult.Success -> {
                adapter.addData(movieResult.movies)

                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.gone()
            }
        }
    }

    companion object {
        private const val TAG = "MovieListFragment"

        private const val ARGS_TYPE = "index"

        fun getInstance(type: Int) = MovieListFragment().apply {
            arguments = Bundle().apply {
                putInt(ARGS_TYPE, type)
            }
        }
    }
}
