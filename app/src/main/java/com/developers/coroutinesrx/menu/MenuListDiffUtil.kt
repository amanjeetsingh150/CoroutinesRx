package com.developers.coroutinesrx.menu

import androidx.recyclerview.widget.DiffUtil
import com.developers.coroutinesrx.data.menu.Menu

class MenuListDiffUtil : DiffUtil.ItemCallback<Menu>() {

    override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
        return oldItem.name == newItem.name
    }

}