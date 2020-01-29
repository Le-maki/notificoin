package com.github.lemaki.notificoin.data.transformers

import com.github.lemaki.notificoin.data.database.entity.SearchWithAdsEntity
import com.github.lemaki.notificoin.domain.SearchWithAds

fun SearchWithAdsEntity.toSearchWithAds() = SearchWithAds(search = search.toSearch(), ads = ads.map { it.toAd() })