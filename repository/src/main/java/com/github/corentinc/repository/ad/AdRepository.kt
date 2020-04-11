package com.github.corentinc.repository.ad

import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.search.Search
import com.github.corentinc.repository.webpage.DocumentToAdJsonArrayTransformer
import com.github.corentinc.repository.webpage.WebPageRepository
import org.joda.time.format.DateTimeFormat
import java.text.ParseException

class AdRepository(
    private val webPageRepository: WebPageRepository,
    private val documentToAdJsonArrayTransformer: DocumentToAdJsonArrayTransformer,
    private val adDataSource: AdDataSource,
    private val adComparator: Comparator<Ad>
) {
    companion object {
        private const val SUBJECT_ATTRIBUTE = "subject"
        private const val PUBLICATION_ATTRIBUTE = "index_date"
        private const val URL = "url"
    }

    fun updateAdsFromWebPage(search: Search): List<Ad> {
        val adList = getSortedRemoteAds(search.url)
        adDataSource.putAll(adList, search.id)
        return adList
    }

    fun putAll(adList: List<Ad>, searchId: Int) {
        adDataSource.putAll(adList, searchId)
    }

    fun getAds() = adDataSource.getAll()

    fun getSortedRemoteAds(url: String): List<Ad> {
        val document = webPageRepository.getWebPage(url)
        return documentToAdJsonArrayTransformer.transform(document)?.map { jsonElement ->
            Ad(
                jsonElement.asJsonObject[SUBJECT_ATTRIBUTE].asString,
                DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(
                    jsonElement.asJsonObject[PUBLICATION_ATTRIBUTE].asString
                ),
                jsonElement.asJsonObject[URL].asString
            )
        }?.sortedWith(adComparator) ?: throw ParseException("Unable to parse $url", 0)
    }

    fun deleteAll() = adDataSource.deleteAll()

    fun getAds(url: String): List<Ad> {
        return adDataSource.getAds(url)
    }
}