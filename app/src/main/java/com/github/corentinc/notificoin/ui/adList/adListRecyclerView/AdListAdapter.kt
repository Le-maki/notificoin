package com.github.corentinc.notificoin.ui.adList.adListRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.github.corentinc.core.ui.adList.AdViewData

class AdListAdapter: Adapter<AdViewHolder>() {
    lateinit var adViewDataList: MutableList<AdViewData>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        return AdViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return adViewDataList.size
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        holder.bind(adViewDataList[position])
    }

    fun isListInitialised() = ::adViewDataList.isInitialized

}
