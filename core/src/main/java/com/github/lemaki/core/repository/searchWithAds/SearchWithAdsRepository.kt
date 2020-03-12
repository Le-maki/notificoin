package com.github.lemaki.core.repository.searchWithAds

import com.github.lemaki.core.SearchWithAds

interface SearchWithAdsRepository {
    fun getAllSortedSearchWithAds(): List<SearchWithAds>
    fun getRemoteSortedSearchWithAds(): List<SearchWithAds>
    fun updateAllSearchWithAds()
    fun replaceAll(searchWithAdsList: List<SearchWithAds>)
}
