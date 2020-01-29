package com.github.lemaki.notificoin.data.searchWithAds

import androidx.room.Embedded
import androidx.room.Relation
import com.github.lemaki.notificoin.data.ad.AdEntity
import com.github.lemaki.notificoin.data.search.SearchEntity

data class SearchWithAdsEntity (
	@Embedded val search: SearchEntity,
	@Relation(
		parentColumn = "searchUrl",
		entityColumn = "adSearchUrl"
	)
	val ads: List<AdEntity>)