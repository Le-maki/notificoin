package com.github.corentinc.repository.searchWithAds

class SearchWithAdsDataSource(
    private val searchWithAdsDao: SearchWithAdsDao
) {
    fun getAllSearchWithAds() = searchWithAdsDao.getAllSearchWithAds().map { it.toSearchWithAds() }
    fun getSearchWithAds(id: Int) = searchWithAdsDao.getSearchWithAds(id).toSearchWithAds()
}