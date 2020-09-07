package com.github.corentinc.repository.ad

import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.search.Search
import com.github.corentinc.logger.NotifiCoinLogger
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
        private const val PUBLICATION_DATE_ATTRIBUTE = "index_date"
        private const val PRICE_ATTRIBUTE = "price"
        private const val URL_ATTRIBUTE = "url"
    }

    fun updateAdsFromWebPage(search: Search): List<Ad> {
        val remoteAdList = getSortedRemoteAds(search.url)
        val localAdList = getAll(search.id)
        val mergedDistinctAdList = (remoteAdList + localAdList).distinct()
        deleteAll(search.id)
        adDataSource.putAll(mergedDistinctAdList, search.id)
        return mergedDistinctAdList
    }

    fun putAll(adList: List<Ad>, searchId: Int) {
        adDataSource.putAll(adList, searchId)
    }

    fun getAll() = adDataSource.getAll()

    fun getSortedRemoteAds(url: String): List<Ad> {
        val document = webPageRepository.getWebPage(url)
        return try {
            documentToAdJsonArrayTransformer.transform(document)?.map { jsonElement ->
                Ad(
                    jsonElement.asJsonObject[SUBJECT_ATTRIBUTE].asString,
                    DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(
                        jsonElement.asJsonObject[PUBLICATION_DATE_ATTRIBUTE].asString
                    ),
                    jsonElement.asJsonObject.get(PRICE_ATTRIBUTE)?.asInt,
                    jsonElement.asJsonObject[URL_ATTRIBUTE].asString
                )
            }?.sortedWith(adComparator) ?: throw ParseException("Unable to parse $url", 0)
        } catch (exception: IllegalStateException) {
            NotifiCoinLogger.i("Empty search : $url")
            emptyList()
        }
    }

    fun deleteAll() = adDataSource.deleteAll()

    fun deleteAll(searchId: Int) = adDataSource.deleteAll(searchId)

    fun getAll(searchId: Int): List<Ad> {
        return adDataSource.getAds(searchId)
    }
}