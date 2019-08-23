package com.developers.coroutinesrx.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.developers.coroutinesrx.data.MovieResult
import com.developers.coroutinesrx.databinding.ItemGridMoviesBinding

class MoviesListAdapter : ListAdapter<MovieResult, MoviesListViewHolder>(MovieListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemGridMoviesBinding.inflate(layoutInflater, parent, false)
        return MoviesListViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}