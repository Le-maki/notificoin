package com.github.lemaki.notificoin.ui.ad

import com.github.lemaki.notificoin.domain.ad.Ad

class AdListToAdsListViewModelTransformer {
    fun transform(adList: List<Ad>): AdListViewModel {
        return AdListViewModel(adList.joinToString(", ") { "${it.title} : ${it.publicationDate.toString("HH:mm")}" })
    }
}