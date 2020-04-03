package com.github.corentinc.core.repository.searchWithAds

import com.github.corentinc.core.SearchAdsPosition

interface SearchAdsPositionRepository {
    fun getAllSortedSearchAdsPosition(): List<SearchAdsPosition>
    fun getRemoteSortedSearchAdsPosition(): List<SearchAdsPosition>
    fun updateAllSearchAdsPositionFromWebPage()
    fun replaceAll(searchAdsPositionList: List<SearchAdsPosition>)
    fun delete(searchId: Int)
}
