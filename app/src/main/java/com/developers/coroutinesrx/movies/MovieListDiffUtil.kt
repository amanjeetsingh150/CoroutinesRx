package com.developers.coroutinesrx.movies

import androidx.recyclerview.widget.DiffUtil
import com.developers.coroutinesrx.data.ResultsItem

class MovieListDiffUtil : DiffUtil.ItemCallback<ResultsItem>() {

    override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.id == newItem.id
    }

}