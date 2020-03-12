package com.github.lemaki.repository.searchWithAds

import com.github.lemaki.core.SearchWithAds
import com.github.lemaki.repository.ad.toAd
import com.github.lemaki.repository.search.toSearch

fun SearchWithAdsEntity.toSearchWithAds() = SearchWithAds(
    search = search.toSearch(),
    ads = ads.map { it.toAd() }
)