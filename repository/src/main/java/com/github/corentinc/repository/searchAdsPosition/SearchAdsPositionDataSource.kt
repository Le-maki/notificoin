package com.github.corentinc.repository.searchAdsPosition

class SearchAdsPositionDataSource(
    private val searchAdsPositionDao: SearchAdsPositionDao
) {
    fun getAllSearchAdsPosition() =
        searchAdsPositionDao.getAllSearchAdsPosition().map { it.toSearchWithAds() }

    fun getSearchAdsPosition(id: Int) =
        searchAdsPositionDao.getSearchAdsPosition(id).toSearchWithAds()
}