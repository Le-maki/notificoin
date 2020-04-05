package com.github.corentinc.notificoin.ui.adList.adListRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.github.corentinc.notificoin.ui.adList.AdViewModel

class AdListAdapter: Adapter<AdViewHolder>() {
    lateinit var adViewModelList: MutableList<AdViewModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        return AdViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return adViewModelList.size
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        holder.bind(adViewModelList[position])
    }

    fun isListInitialised() = ::adViewModelList.isInitialized

}
