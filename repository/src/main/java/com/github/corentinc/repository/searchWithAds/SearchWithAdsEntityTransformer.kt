package com.github.corentinc.repository.searchWithAds

import com.github.corentinc.core.SearchWithAds
import com.github.corentinc.repository.ad.toAd
import com.github.corentinc.repository.search.toSearch

fun SearchWithAdsEntity.toSearchWithAds() = SearchWithAds(
    search = search.toSearch(),
    ads = ads.map { it.toAd() }
)