package com.github.corentinc.repository.ad

import com.github.corentinc.core.ad.Ad

fun AdEntity.toAd() = Ad(id = adId, title = adTitle, publicationDate = publicationDate, url = url)

fun Ad.toEntity(searchId: Int) = AdEntity(
    adId = id,
    adTitle = title,
    searchId = searchId,
    publicationDate = publicationDate,
    url = url
)