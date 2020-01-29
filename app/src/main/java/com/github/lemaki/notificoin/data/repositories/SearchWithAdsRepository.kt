package com.github.lemaki.notificoin.data.repositories

import com.github.lemaki.notificoin.data.dataSources.SearchWithAdsDataSource

class SearchWithAdsRepository(private val searchWithAdsDataSource: SearchWithAdsDataSource) {
	fun getSearchWithAds() = searchWithAdsDataSource.getSearchWithAds()
}