package com.github.lemaki.notificoin.ui.ad

import com.github.lemaki.core.ad.Ad

class AdListToAdsListViewModelTransformer {
    fun transform(adList: List<Ad>): AdListTextViewModel {
        return AdListTextViewModel(adList.joinToString(", ") {
            "${it.title} : ${it.publicationDate.toString(
                "HH:mm"
            )}"
        })
    }
}