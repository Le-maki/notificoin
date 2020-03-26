package com.github.lemaki.core.repository.searchWithAds

import com.github.lemaki.core.SearchWithAds
import com.github.lemaki.core.search.Search

interface SearchWithAdsRepository {
    fun getAllSortedSearchWithAds(): List<SearchWithAds>
    fun getRemoteSortedSearchWithAds(): List<SearchWithAds>
    fun updateAllSearchWithAds()
    fun replaceAll(searchWithAdsList: List<SearchWithAds>)
    fun delete(search: Search)
}
