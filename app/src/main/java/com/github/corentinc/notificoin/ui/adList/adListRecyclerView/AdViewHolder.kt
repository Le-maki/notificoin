package com.github.corentinc.notificoin.ui.adList.adListRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.corentinc.core.ui.adList.AdViewData
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.createIntentFromUrl

class AdViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.item_ad_recyclerview,
        parent,
        false
    )
) {
    private var adItemAdTitle: TextView = itemView.findViewById(R.id.adItemAdTitle)
    private var adItemHour: TextView = itemView.findViewById(R.id.adItemHour)
    private var adItemDate: TextView = itemView.findViewById(R.id.adItemDate)
    private var adItemSearchTitle: TextView = itemView.findViewById(R.id.adItemSearchTitle)
    private var adItemPrice: TextView = itemView.findViewById(R.id.adItemPrice)

    fun bind(adViewData: AdViewData) {
        with(adViewData) {
            adItemAdTitle.text = adTitle
            adItemHour.text = hour
            adItemDate.text = date
            adItemSearchTitle.text = searchTitle
            adItemPrice.text = price?.let {
                itemView.resources.getString(R.string.adViewHolderprice, price)
            }
        }
        itemView.findViewById<ConstraintLayout>(R.id.adItem).setOnClickListener {
            itemView.context.startActivity(adViewData.url.createIntentFromUrl())
        }

    }
}
