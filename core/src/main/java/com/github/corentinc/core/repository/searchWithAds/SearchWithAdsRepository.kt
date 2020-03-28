package com.github.corentinc.core.repository.searchWithAds

import com.github.corentinc.core.SearchWithAds
import com.github.corentinc.core.search.Search

interface SearchWithAdsRepository {
    fun getAllSortedSearchWithAds(): List<SearchWithAds>
    fun getRemoteSortedSearchWithAds(): List<SearchWithAds>
    fun updateAllSearchWithAds()
    fun replaceAll(searchWithAdsList: List<SearchWithAds>)
    fun delete(search: Search)
}
