package com.github.lemaki.repository.searchWithAds

import androidx.room.Embedded
import androidx.room.Relation
import com.github.lemaki.repository.ad.AdEntity
import com.github.lemaki.repository.search.SearchEntity

data class SearchWithAdsEntity(
    @Embedded val search: SearchEntity,
    @Relation(
        parentColumn = "searchUrl",
        entityColumn = "adSearchUrl"
    )
    val ads: List<AdEntity>
)