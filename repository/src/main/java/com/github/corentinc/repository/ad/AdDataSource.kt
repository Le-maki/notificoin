package com.github.corentinc.repository.ad

import com.github.corentinc.core.ad.Ad


class AdDataSource(
    private val adDao: AdDao
) {
    fun getAd(id: Int): Ad {
        return adDao.getAd(id).toAd()
    }

    fun putAd(ad: Ad, searchId: Int) {
        adDao.insertOrReplace(ad.toEntity(searchId))
    }

    fun putAll(adList: List<Ad>, searchId: Int) {
        adDao.insertAllOrReplace(adList.map { it.toEntity(searchId) })
    }

    fun getAll() = adDao.getAll().map { it.toAd() }

    fun deleteAll() = adDao.deleteAll()

    fun getAds(url: String): List<Ad> {
        return adDao.getAds(url).map { it.toAd() }
    }
}