package com.github.lemaki.notificoin.data.searchWithAds

import com.github.lemaki.notificoin.data.ad.AdRepository
import com.github.lemaki.notificoin.data.search.SearchRepository

class SearchWithAdsRepository(
	private val searchWithAdsDataSource: SearchWithAdsDataSource,
	private val searchRepository: SearchRepository,
	private val adRepository: AdRepository
) {
	fun getSearchWithAds() = searchWithAdsDataSource.getSearchWithAds()
	fun updateAllSearchWithAds() {
		adRepository.deleteAll()
		searchRepository.getAllSearches().forEach {
			adRepository.updateAdsFromWebPage(it.url)
		}
	}
}