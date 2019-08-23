package com.developers.coroutinesrx.movies

import androidx.recyclerview.widget.RecyclerView
import com.developers.coroutinesrx.data.ResultsItem
import com.developers.coroutinesrx.databinding.ItemGridMoviesBinding
import com.squareup.picasso.Picasso

class MoviesListViewHolder(private val binding: ItemGridMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(resultsItem: ResultsItem) {
        binding.movie = resultsItem
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${resultsItem.posterPath}").into(binding.posterImageView)
        binding.executePendingBindings()
    }
}