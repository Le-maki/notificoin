package com.github.corentinc.repository.searchAdsPosition

import androidx.room.Embedded
import androidx.room.Relation
import com.github.corentinc.repository.ad.AdEntity
import com.github.corentinc.repository.search.SearchEntity
import com.github.corentinc.repository.searchPosition.SearchPositionEntity

data class SearchAdsPositionEntity(
    @Embedded val search: SearchEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "searchId"
    )
    val ads: List<AdEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "searchId"
    )
    val searchPosition: SearchPositionEntity
)