package com.github.corentinc.repository.searchWithAds

import com.github.corentinc.core.SearchWithAds
import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.repository.searchWithAds.SearchWithAdsRepository
import com.github.corentinc.core.search.Search
import com.github.corentinc.repository.ad.AdRepository

class SearchWithAdsRepositoryImpl(
    private val searchWithAdsDataSource: SearchWithAdsDataSource,
    private val searchRepository: SearchRepository,
    private val adRepository: AdRepository,
    private val adComparator: Comparator<Ad>
): SearchWithAdsRepository {
    override fun getAllSortedSearchWithAds() = searchWithAdsDataSource.getAllSearchWithAds().map {
        it.copy(ads = it.ads.sortedWith(adComparator))
    }

    override fun getRemoteSortedSearchWithAds() = searchRepository.getAllSearches().map { search ->
        SearchWithAds(search, adRepository.getSortedRemoteAds(search.url))
    }

    override fun updateAllSearchWithAds() {
        adRepository.deleteAll()
        searchRepository.getAllSearches().forEach {
            adRepository.updateAdsFromWebPage(it.url)
        }
    }

    override fun replaceAll(searchWithAdsList: List<SearchWithAds>) {
        adRepository.deleteAll()
        searchRepository.deleteAll()
        searchWithAdsList.forEach {
            searchRepository.addSearch(it.search)
            adRepository.putAll(it.ads, it.search.url)
        }

    }

    override fun delete(search: Search) {
        searchRepository.delete(search)
        adRepository.delete(adRepository.getAds(search.url))
    }
}