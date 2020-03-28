package com.github.corentinc.repository.searchWithAds

import androidx.room.Embedded
import androidx.room.Relation
import com.github.corentinc.repository.ad.AdEntity
import com.github.corentinc.repository.search.SearchEntity
import com.github.corentinc.repository.searchPosition.SearchPositionEntity

data class SearchAdsPositionEntity(
    @Embedded val search: SearchEntity,
    @Relation(
        parentColumn = "searchUrl",
        entityColumn = "adSearchUrl"
    )
    val ads: List<AdEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "searchId"
    )
    val searchPosition: SearchPositionEntity
)