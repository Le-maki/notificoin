package com.github.corentinc.core

import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.search.Search
import com.github.corentinc.core.search.SearchPosition

data class SearchAdsPosition(
    val search: Search,
    val ads: List<Ad>,
    val searchPosition: SearchPosition
)