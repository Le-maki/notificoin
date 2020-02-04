package com.github.lemaki.notificoin.data.searchWithAds

import com.github.lemaki.notificoin.data.ad.toAd
import com.github.lemaki.notificoin.data.search.toSearch
import com.github.lemaki.notificoin.domain.SearchWithAds

fun SearchWithAdsEntity.toSearchWithAds() = SearchWithAds(search = search.toSearch(), ads = ads.map { it.toAd() }.sortedBy { it.publicationDate })