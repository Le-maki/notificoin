package com.github.lemaki.notificoin.data.searchWithAds

import com.github.lemaki.notificoin.data.ad.AdRepository
import com.github.lemaki.notificoin.data.search.SearchRepository
import com.github.lemaki.notificoin.domain.SearchWithAds
import com.github.lemaki.notificoin.domain.ad.Ad

class SearchWithAdsRepository(
    private val searchWithAdsDataSource: SearchWithAdsDataSource,
    private val searchRepository: SearchRepository,
    private val adRepository: AdRepository,
    private val adComparator: Comparator<Ad>
) {
    fun getAllSortedSearchWithAds() = searchWithAdsDataSource.getAllSearchWithAds().apply {
        this.forEach { it.ads.sortedWith(adComparator) }
    }

    fun getRemoteSortedSearchWithAds() = searchRepository.getAllSearches().map { search ->
        SearchWithAds(search, adRepository.getSortedRemoteAds(search.url))
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