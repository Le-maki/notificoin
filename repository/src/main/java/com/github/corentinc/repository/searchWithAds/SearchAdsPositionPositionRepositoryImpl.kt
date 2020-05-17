package com.github.corentinc.repository.searchWithAds

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.repository.search.SearchPositionRepository
import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.core.repository.searchAdsPosition.SearchAdsPositionRepository
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
                searchPositionRepository.getSearchPosition(search.id)
            )
    }

    override fun updateAllSearchAdsPositionFromWebPage() {
        adRepository.deleteAll()
        searchRepository.getAllSearches().forEach {
            adRepository.updateAdsFromWebPage(it)
        }
    }

    override fun replaceAll(searchAdsPositionList: List<SearchAdsPosition>) {
        searchRepository.deleteAll()
        searchAdsPositionList.forEach {
            searchRepository.addSearch(it.search)
            adRepository.putAll(it.ads, it.search.id)
        }

    }

    override fun delete(searchId: Int) {
        searchRepository.delete(searchId)
    }
}