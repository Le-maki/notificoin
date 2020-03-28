package com.github.corentinc.notificoin.ui.ad

import com.github.corentinc.core.ad.Ad

class AdListToAdsListViewModelTransformer {
    fun transform(adList: List<Ad>): AdListTextViewModel {
        return AdListTextViewModel(adList.joinToString(", ") {
            "${it.title} : ${it.publicationDate.toString(
                "HH:mm"
            )}"
        })
    }
}