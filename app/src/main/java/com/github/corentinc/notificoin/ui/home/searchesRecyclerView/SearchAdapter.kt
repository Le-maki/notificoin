package com.github.corentinc.notificoin.ui.home.searchesRecyclerView

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.corentinc.core.SearchAdsPosition
import kotlinx.android.synthetic.main.searches_recyclerview_item.view.*

class SearchAdapter(
    swipeAndDragHelper: SwipeAndDragHelper
):
    RecyclerView.Adapter<SearchViewHolder>(), ActionCompletion {
    lateinit var searchAdsPositionList: MutableList<SearchAdsPosition>
    val touchHelper: ItemTouchHelper = ItemTouchHelper(swipeAndDragHelper)
    lateinit var searchAdapterListener: SearchAdapterListener

    init {
        swipeAndDragHelper.actionCompletion = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return searchAdsPositionList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.itemView.searchItemReorderImageView.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                touchHelper.startDrag(holder)
            }
            false
        }
        holder.bind(searchAdsPositionList[position])
    }

    override fun onViewMoved(oldPosition: Int, newPosition: Int) {
        val movedSearch: SearchAdsPosition = searchAdsPositionList[oldPosition]
        val replacedSearch = searchAdsPositionList[newPosition]
        searchAdapterListener.onSearchMoved(Pair(movedSearch.search.id, replacedSearch.search.id))
        searchAdsPositionList.removeAt(oldPosition)
        searchAdsPositionList.add(newPosition, movedSearch)
        notifyItemMoved(oldPosition, newPosition)
    }

    override fun onViewSwiped(position: Int) {
        val removedSearch = searchAdsPositionList.removeAt(position)
        searchAdapterListener.onSearchDeleted(removedSearch)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }
}