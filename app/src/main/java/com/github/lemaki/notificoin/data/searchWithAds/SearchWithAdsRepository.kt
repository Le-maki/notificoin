package com.github.lemaki.notificoin.data.searchWithAds

class SearchWithAdsRepository(private val searchWithAdsDataSource: SearchWithAdsDataSource) {
	fun getSearchWithAds() = searchWithAdsDataSource.getSearchWithAds()
}