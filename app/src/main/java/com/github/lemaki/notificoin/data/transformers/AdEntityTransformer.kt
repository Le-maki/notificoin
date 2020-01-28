package com.github.lemaki.notificoin.data.transformers

import com.github.lemaki.notificoin.data.database.entity.AdEntity
import com.github.lemaki.notificoin.domain.ad.Ad

fun AdEntity.toAd() = Ad(id = id, title = title)

fun Ad.toEntity() = AdEntity(id = id, title = title)