package com.github.lemaki.notificoin.data.repositories

import com.github.lemaki.notificoin.data.dataSources.AdDataSource
import com.github.lemaki.notificoin.data.transformers.DocumentToAdJsonArrayTransformer
import com.github.lemaki.notificoin.domain.ad.Ad
import java.text.ParseException

class AdRepository(
	private val webPageRepository: WebPageRepository,
	private val documentToAdJsonArrayTransformer: DocumentToAdJsonArrayTransformer,
	private val adDataSource: AdDataSource
) {
	companion object {
		private const val SUBJECT_ATTRIBUTE = "subject"
		private const val ID_ATTRIBUTE = "list_id"
	}

	fun updateAdsFromWebPage(url: String = "https://www.leboncoin.fr/recherche/?category=2&text=voiture&locations=Nantes"): List<Ad> {
		val document = webPageRepository.getWebPage(url)
		val adList = documentToAdJsonArrayTransformer.transform(document)?.let { jsonArray ->
			jsonArray.map { jsonElement ->
				Ad(jsonElement.asJsonObject[ID_ATTRIBUTE].asInt, jsonElement.asJsonObject[SUBJECT_ATTRIBUTE].asString)
			}
		} ?: throw ParseException("Unable to parse $url", 0)
		adDataSource.putAll(adList)
		return adList
	}

	fun getAds(): List<Ad> = adDataSource.getAll()
}