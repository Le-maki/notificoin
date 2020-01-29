package com.github.lemaki.notificoin.data.searchWithAds

class SearchWithAdsDataSource(
	private val searchWithAdsDao: SearchWithAdsDao
) {
	fun getSearchWithAds() = searchWithAdsDao.getSearchWithAds().map { it.toSearchWithAds() }
}