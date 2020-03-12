package com.github.lemaki.core

import com.github.lemaki.core.ad.Ad
import com.github.lemaki.core.search.Search

data class SearchWithAds(
    val search: Search,
    val ads: List<Ad>
)