package com.github.corentinc.repository.ad

import com.github.corentinc.core.ad.Ad


class AdDataSource(
    private val adDao: AdDao
) {
    fun getAd(id: Int): Ad {
        return adDao.getAd(id).toAd()
    }

    fun putAd(ad: Ad, searchUrl: String) {
        adDao.insertOrReplace(ad.toEntity(searchUrl))
    }

    fun putAll(adList: List<Ad>, searchUrl: String) {
        adDao.insertAllOrReplace(adList.map { it.toEntity(searchUrl) })
    }

    fun getAll() = adDao.getAll().map { it.toAd() }

    fun deleteAll() = adDao.deleteAll()

    fun delete(adList: List<Ad>) {
        adDao.delete(adList.map { it.id })
    }

    fun getAds(url: String): List<Ad> {
        return adDao.getAds(url).map { it.toAd() }
    }
}