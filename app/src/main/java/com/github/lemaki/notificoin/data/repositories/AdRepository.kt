package com.github.lemaki.notificoin.data.repositories

import com.github.lemaki.notificoin.data.transformers.DocumentToAdJsonArrayTransformer
import com.github.lemaki.notificoin.domain.ad.Ad
import java.text.ParseException

class AdRepository(
	private val webPageRepository: WebPageRepository,
	private val documentToAdJsonArrayTransformer: DocumentToAdJsonArrayTransformer
) {
	companion object {
		private const val SUBJECT_ATTRIBUTE = "subject"
	}
	fun getAds(url: String = "https://www.leboncoin.fr/recherche/?category=2&text=voiture&locations=Nantes"): List<Ad> {
		val document = webPageRepository.getWebPage(url)
		return documentToAdJsonArrayTransformer.transform(document)?.let { jsonArray ->
			jsonArray.map { jsonElement ->
				Ad(jsonElement.asJsonObject[SUBJECT_ATTRIBUTE].asString)
			}
		} ?: throw ParseException("Unable to parse $url", 0)
	}
}