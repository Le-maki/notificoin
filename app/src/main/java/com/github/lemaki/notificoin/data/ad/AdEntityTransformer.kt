package com.github.lemaki.notificoin.data.ad

import com.github.lemaki.notificoin.domain.ad.Ad

fun AdEntity.toAd() = Ad(id = adId, title = adTitle, publicationDate = publicationDate, url = url)

fun Ad.toEntity(searchUrl: String) = AdEntity(
    adId = id,
    adTitle = title,
    searchUrl = searchUrl,
    publicationDate = publicationDate,
    url = url
)