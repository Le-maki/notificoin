package com.github.corentinc.core.repository.searchWithAds

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.search.Search

interface SearchAdsPositionRepository {
    fun getAllSortedSearchAdsPosition(): List<SearchAdsPosition>
    fun getRemoteSortedSearchAdsPosition(): List<SearchAdsPosition>
    fun updateAllSearchAdsPositionFromWebPage()
    fun replaceAll(searchAdsPositionList: List<SearchAdsPosition>)
    fun delete(search: Search)
}
