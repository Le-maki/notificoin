package com.github.corentinc.core

import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.search.Search

data class SearchWithAds(
    val search: Search,
    val ads: List<Ad>
)