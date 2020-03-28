package com.github.corentinc.repository.searchPosition

import com.github.corentinc.core.search.SearchPosition

fun SearchPositionEntity.toSearchPosition() = SearchPosition(
    position = position,
    searchId = searchId
)

fun SearchPosition.toEntity() = SearchPositionEntity(
    position = position,
    searchId = searchId
)