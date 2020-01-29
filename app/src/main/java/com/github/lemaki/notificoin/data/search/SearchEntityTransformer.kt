package com.github.lemaki.notificoin.data.search

import com.github.lemaki.notificoin.domain.search.Search

fun SearchEntity.toSearch() = Search(url = searchUrl, title = searchTitle)
fun Search.toEntity() = SearchEntity(searchUrl = url, searchTitle = title)