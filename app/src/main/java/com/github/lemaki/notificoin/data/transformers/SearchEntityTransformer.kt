package com.github.lemaki.notificoin.data.transformers

import com.github.lemaki.notificoin.data.database.entity.SearchEntity
import com.github.lemaki.notificoin.domain.search.Search

fun SearchEntity.toSearch() = Search(url = searchUrl, title = searchTitle)
fun Search.toEntity() = SearchEntity(searchUrl = url, searchTitle = title)