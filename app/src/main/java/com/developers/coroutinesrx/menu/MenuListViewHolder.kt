package com.developers.coroutinesrx.menu

import androidx.recyclerview.widget.RecyclerView
import com.developers.coroutinesrx.data.menu.Menu
import com.developers.coroutinesrx.databinding.ItemMenuBinding

class MenuListViewHolder(private val binding: ItemMenuBinding, private val onItemClick: (menu: Menu) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(menu: Menu) {
        binding.menu = menu
        binding.menuViewHolder = this
        binding.executePendingBindings()
    }

    fun onMenuItemClick(menu: Menu) {
        onItemClick(menu)
    }
}