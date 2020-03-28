package com.github.corentinc.notificoin.ui.home.searchesRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.home.HomeFragmentDirections

class SearchViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.searches_recyclerview_item,
            parent,
            false
        )
    ) {
    private var title: TextView = itemView.findViewById(R.id.searchItemTitle)
    private var button: ImageButton = itemView.findViewById(R.id.searchItemButton)

    fun bind(searchAdsPosition: SearchAdsPosition) {
        title.text = searchAdsPosition.search.title
        button.setOnClickListener {
            Navigation.findNavController(itemView)
                .navigate(
                    HomeFragmentDirections.editSearchAction(
                        searchAdsPosition.search.id,
                        searchAdsPosition.search.url,
                        searchAdsPosition.search.title
                    )
                )
        }
    }
}