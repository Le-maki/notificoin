package com.github.lemaki.notificoin.ui.home.searchesRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.lemaki.core.search.Search
import com.github.lemaki.notificoin.R

class SearchViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.searches_recyclerview_item,
            parent,
            false
        )
    ) {
    private var title: TextView = itemView.findViewById(R.id.searchItemTitle)
    private var button: Button = itemView.findViewById(R.id.searchItemButton)

    init {
    }

    fun bind(search: Search) {
        title.text = search.title
        button.setOnClickListener {
            Toast.makeText(itemView.context, "CLICKED", Toast.LENGTH_SHORT).show()
        }
    }
}