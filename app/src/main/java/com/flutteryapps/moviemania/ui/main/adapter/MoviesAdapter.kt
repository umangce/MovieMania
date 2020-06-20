package com.flutteryapps.moviemania.ui.main.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.flutteryapps.moviemania.R
import com.flutteryapps.moviemania.common.factory.GlideApp
import com.flutteryapps.moviemania.common.utils.gone
import com.flutteryapps.moviemania.common.utils.visible
import com.flutteryapps.moviemania.databinding.ItemMovieBinding
import com.flutteryapps.moviemania.network.Movie
import com.flutteryapps.moviemania.network.client.IMAGE_BASE_URL

/**
 * Created by umang on 13/06/20.
 */
class MoviesAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val movieList = ArrayList<Movie>()

    fun addData(newMovieList: List<Movie>) {
        val position = movieList.size
        movieList.addAll(newMovieList)
        notifyItemRangeInserted(position, newMovieList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieItemViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(context))
        )
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieItemViewHolder) {
            holder.movieDetails(movieList[position])
        }
    }

    private inner class MovieItemViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun movieDetails(movie: Movie) {
            binding.textViewMovieName.text = movie.originalTitle ?: movie.name
            binding.imageViewMovie.gone()
            binding.progressLayout.visible()
            GlideApp.with(context)
                .asBitmap()
                .load("${IMAGE_BASE_URL}${movie.posterPath}")
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.imageViewMovie.setImageBitmap(resource)
                        createPaletteAsync(binding.viewBackGround, resource)

                        binding.progressLayout.gone()
                        binding.imageViewMovie.visible()
                    }
                })
        }
    }

    fun createPaletteAsync(view: View, bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->
            if (palette != null) {
                view.setBackgroundColor(
                    palette.getVibrantColor(
                        ContextCompat.getColor(
                            context,
                            R.color.black_translucent_60
                        )
                    )
                )
            }
        }
    }
}