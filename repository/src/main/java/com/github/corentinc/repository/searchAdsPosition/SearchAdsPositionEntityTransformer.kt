package com.github.corentinc.repository.searchAdsPosition

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.repository.ad.toAd
import com.github.corentinc.repository.search.toSearch
import com.github.corentinc.repository.searchPosition.toSearchPosition

fun SearchAdsPositionEntity.toSearchWithAds() = SearchAdsPosition(
    search = search.toSearch(),
    ads = ads.map { it.toAd() },
    searchPosition = searchPosition.toSearchPosition()
)