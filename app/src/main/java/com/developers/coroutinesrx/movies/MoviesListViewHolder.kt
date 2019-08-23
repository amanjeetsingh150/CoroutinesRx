package com.developers.coroutinesrx.movies

import androidx.recyclerview.widget.RecyclerView
import com.developers.coroutinesrx.data.MovieResult
import com.developers.coroutinesrx.databinding.ItemGridMoviesBinding
import com.squareup.picasso.Picasso

class MoviesListViewHolder(private val binding: ItemGridMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieResult: MovieResult) {
        binding.movie = movieResult
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${movieResult.posterPath}").into(binding.posterImageView)
        binding.executePendingBindings()
    }
}