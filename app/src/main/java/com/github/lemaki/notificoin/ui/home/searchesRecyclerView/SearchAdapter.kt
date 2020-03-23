package com.github.lemaki.notificoin.ui.home.searchesRecyclerView

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.lemaki.core.search.Search
import kotlinx.android.synthetic.main.searches_recyclerview_item.view.*

class SearchAdapter(
    swipeAndDragHelper: SwipeAndDragHelper
):
    RecyclerView.Adapter<SearchViewHolder>(), ActionCompletion {
    lateinit var searchList: MutableList<Search>
    val touchHelper: ItemTouchHelper = ItemTouchHelper(swipeAndDragHelper)

    init {
        swipeAndDragHelper.actionCompletion = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.itemView.searchItemReorderImageView.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                touchHelper.startDrag(holder)
            }
            false
        }
        holder.bind(searchList[position])
    }

    override fun onViewMoved(oldPosition: Int, newPosition: Int) {
        val targetSearch: Search = searchList[oldPosition]
        searchList.removeAt(oldPosition)
        searchList.add(newPosition, targetSearch)
        notifyItemMoved(oldPosition, newPosition)
    }

    override fun onViewSwiped(position: Int) {
        searchList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}