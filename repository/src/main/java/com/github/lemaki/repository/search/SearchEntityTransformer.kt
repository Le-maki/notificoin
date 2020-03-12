package com.github.lemaki.repository.search

import com.github.lemaki.core.search.Search

fun SearchEntity.toSearch() = Search(url = searchUrl, title = searchTitle)
fun Search.toEntity() = SearchEntity(searchUrl = url, searchTitle = title)