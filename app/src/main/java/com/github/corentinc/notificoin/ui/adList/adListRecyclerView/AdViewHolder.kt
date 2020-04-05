package com.github.corentinc.notificoin.ui.adList.adListRecyclerView

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.adList.AdViewModel

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

    fun bind(adViewModel: AdViewModel) {
        with(adViewModel) {
            adItemAdTitle.text = this.adTitle
            adItemHour.text = this.hour
            adItemDate.text = this.date
            adItemSearchTitle.text = this.searchTitle
        }
        itemView.findViewById<ConstraintLayout>(R.id.adItem).setOnClickListener {
            itemView.context.startActivity(createOpenAdIntent(adViewModel.url))
        }

    }

    private fun createOpenAdIntent(url: String): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return intent
    }

}
