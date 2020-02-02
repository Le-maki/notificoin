package com.github.lemaki.notificoin.data.ad

import com.github.lemaki.notificoin.domain.ad.Ad

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
}