package com.github.lemaki.notificoin.data.dataSources

import com.github.lemaki.notificoin.data.database.dao.AdDao
import com.github.lemaki.notificoin.data.transformers.toAd
import com.github.lemaki.notificoin.data.transformers.toEntity
import com.github.lemaki.notificoin.domain.ad.Ad

class AdDataSource(
	private val adDao: AdDao
) {
	fun getAd(id: Int): Ad {
		return adDao.getAd(id).toAd()
	}

	fun putAd(ad: Ad) {
		adDao.insertOrReplace(ad.toEntity())
	}

	fun putAll(adList: List<Ad>) {
		adDao.insertAllOrReplace(adList.map { it.toEntity() })
	}

	fun getAll() = adDao.getAll().map { it.toAd() }
}