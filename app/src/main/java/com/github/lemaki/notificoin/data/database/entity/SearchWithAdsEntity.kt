package com.github.lemaki.notificoin.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SearchWithAdsEntity (
	@Embedded val search: SearchEntity,
	@Relation(
		parentColumn = "searchUrl",
		entityColumn = "adSearchUrl"
	)
	val ads: List<AdEntity>)