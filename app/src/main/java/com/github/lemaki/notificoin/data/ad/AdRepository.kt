package com.github.lemaki.notificoin.data.ad

import com.github.lemaki.notificoin.data.webpage.WebPageRepository
import com.github.lemaki.notificoin.data.webpage.DocumentToAdJsonArrayTransformer
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

	fun updateAdsFromWebPage(url: String): List<Ad> {
		val document = webPageRepository.getWebPage(url)
		val adList = documentToAdJsonArrayTransformer.transform(document)?.let { jsonArray ->
			jsonArray.map { jsonElement ->
				Ad(jsonElement.asJsonObject[ID_ATTRIBUTE].asInt, jsonElement.asJsonObject[SUBJECT_ATTRIBUTE].asString)
			}
		} ?: throw ParseException("Unable to parse $url", 0)
		adDataSource.putAll(adList, url)
		return adList
	}

	fun getAds() = adDataSource.getAll()
}