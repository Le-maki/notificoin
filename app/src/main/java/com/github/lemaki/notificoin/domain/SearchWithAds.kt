package com.github.lemaki.notificoin.domain

import com.github.lemaki.notificoin.domain.ad.Ad
import com.github.lemaki.notificoin.domain.search.Search

data class SearchWithAds (
	val search: Search,
	val ads: List<Ad>
)