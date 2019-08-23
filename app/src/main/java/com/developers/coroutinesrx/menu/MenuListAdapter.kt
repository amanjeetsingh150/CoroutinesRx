package com.developers.coroutinesrx.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.developers.coroutinesrx.data.menu.Menu
import com.developers.coroutinesrx.databinding.ItemMenuBinding

class MenuListAdapter(private val onItemClick: (menu: Menu) -> Unit) :
    ListAdapter<Menu, MenuListViewHolder>(MenuListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemMenuBinding.inflate(layoutInflater, parent, false)
        return MenuListViewHolder(viewBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: MenuListViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

}