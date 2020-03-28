package com.github.corentinc.repository.ad

import com.github.corentinc.core.ad.Ad

fun AdEntity.toAd() = Ad(id = adId, title = adTitle, publicationDate = publicationDate, url = url)

fun Ad.toEntity(searchUrl: String) = AdEntity(
    adId = id,
    adTitle = title,
    searchUrl = searchUrl,
    publicationDate = publicationDate,
    url = url
)