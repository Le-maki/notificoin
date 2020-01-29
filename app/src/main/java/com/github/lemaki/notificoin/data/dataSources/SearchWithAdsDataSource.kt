package com.github.lemaki.notificoin.data.dataSources

import com.github.lemaki.notificoin.data.database.dao.SearchWithAdsDao
import com.github.lemaki.notificoin.data.transformers.toSearchWithAds

class SearchWithAdsDataSource(
	private val searchWithAdsDao: SearchWithAdsDao
) {
	fun getSearchWithAds() = searchWithAdsDao.getSearchWithAds().map { it.toSearchWithAds() }
}