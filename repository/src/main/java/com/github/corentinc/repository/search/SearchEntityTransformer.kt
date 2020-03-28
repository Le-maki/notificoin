package com.github.corentinc.repository.search

import com.github.corentinc.core.search.Search

fun SearchEntity.toSearch() = Search(id = id, url = searchUrl, title = searchTitle)
fun Search.toEntity() = SearchEntity(id = id, searchUrl = url, searchTitle = title)