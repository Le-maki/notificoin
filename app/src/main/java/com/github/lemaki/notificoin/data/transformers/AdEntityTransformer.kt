package com.github.lemaki.notificoin.data.transformers

import com.github.lemaki.notificoin.data.database.entity.AdEntity
import com.github.lemaki.notificoin.domain.ad.Ad

fun AdEntity.toAd() = Ad(id = adId, title = adTitle)

fun Ad.toEntity(searchUrl: String) = AdEntity(adId = id, adTitle = title, searchUrl = searchUrl)