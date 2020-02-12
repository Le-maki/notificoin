package com.github.lemaki.notificoin.data.searchWithAds

class SearchWithAdsDataSource(
	private val searchWithAdsDao: SearchWithAdsDao
) {
    fun getAllSearchWithAds() = searchWithAdsDao.getAllSearchWithAds().map { it.toSearchWithAds() }
}