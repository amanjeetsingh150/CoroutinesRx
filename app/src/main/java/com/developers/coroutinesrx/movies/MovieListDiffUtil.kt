package com.developers.coroutinesrx.movies

import androidx.recyclerview.widget.DiffUtil
import com.developers.coroutinesrx.data.MovieResult

class MovieListDiffUtil : DiffUtil.ItemCallback<MovieResult>() {

    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return oldItem.id == newItem.id
    }

}