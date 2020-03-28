package com.github.corentinc.repository.searchWithAds

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.repository.search.SearchPositionRepository
import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.repository.searchWithAds.SearchAdsPositionRepository
import com.github.corentinc.core.search.Search
import com.github.corentinc.repository.ad.AdRepository

class SearchAdsPositionPositionRepositoryImpl(
    private val searchAdsPositionDataSource: SearchAdsPositionDataSource,
    private val searchRepository: SearchRepository,
    private val adRepository: AdRepository,
    private val searchPositionRepository: SearchPositionRepository,
    private val adComparator: Comparator<Ad>
): SearchAdsPositionRepository {
    override fun getAllSortedSearchAdsPosition() =
        searchAdsPositionDataSource.getAllSearchAdsPosition().map {
        it.copy(ads = it.ads.sortedWith(adComparator))
    }

    override fun getRemoteSortedSearchAdsPosition() =
        searchRepository.getAllSearches().map { search ->
            SearchAdsPosition(
                search,
                adRepository.getSortedRemoteAds(search.url),
                searchPositionRepository.getPosition(search.id)
            )
    }

    override fun updateAllSearchAdsPositionFromWebPage() {
        adRepository.deleteAll()
        searchRepository.getAllSearches().forEach {
            adRepository.updateAdsFromWebPage(it.url)
        }
    }

    override fun replaceAll(searchAdsPositionList: List<SearchAdsPosition>) {
        adRepository.deleteAll()
        searchRepository.deleteAll()
        searchPositionRepository.deleteAll()
        searchAdsPositionList.forEach {
            searchRepository.addSearch(it.search)
            adRepository.putAll(it.ads, it.search.url)
        }

    }

    override fun delete(search: Search) {
        searchRepository.delete(search)
        adRepository.delete(adRepository.getAds(search.url))
    }
}