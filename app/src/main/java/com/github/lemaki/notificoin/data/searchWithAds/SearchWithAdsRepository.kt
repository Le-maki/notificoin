package com.github.lemaki.notificoin.data.searchWithAds

import com.github.lemaki.notificoin.data.ad.AdRepository
import com.github.lemaki.notificoin.data.search.SearchRepository
import com.github.lemaki.notificoin.domain.SearchWithAds

class SearchWithAdsRepository(
    private val searchWithAdsDataSource: SearchWithAdsDataSource,
    private val searchRepository: SearchRepository,
    private val adRepository: AdRepository
) {
    fun getSearchWithAds() = searchWithAdsDataSource.getSearchWithAds()

    fun getRemoteSearchWithAds() = searchRepository.getAllSearches().map { search ->
        SearchWithAds(search, adRepository.getRemoteAds(search.url))
    }

    fun updateAllSearchWithAds() {
        adRepository.deleteAll()
        searchRepository.getAllSearches().forEach {
            adRepository.updateAdsFromWebPage(it.url)
        }
    }

    fun replaceAll(searchWithAdsList: List<SearchWithAds>) {
        adRepository.deleteAll()
        searchRepository.deleteAll()
        searchWithAdsList.forEach { 
            searchRepository.addSearch(it.search)
            adRepository.putAll(it.ads, it.search.url)
        }
        
    }
}