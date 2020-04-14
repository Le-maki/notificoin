package com.github.corentinc.repository.ad

import com.github.corentinc.core.ad.Ad

fun AdEntity.toAd() =
    Ad(title = adTitle, publicationDate = adPublicationDate, price = adPrice, url = adUrl)

fun Ad.toEntity(searchId: Int) = AdEntity(
    adTitle = title,
    searchId = searchId,
    adPublicationDate = publicationDate,
    adPrice = price,
    adUrl = url
)