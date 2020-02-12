package com.github.lemaki.notificoin.data.ad

import com.github.lemaki.notificoin.data.webpage.DocumentToAdJsonArrayTransformer
import com.github.lemaki.notificoin.data.webpage.WebPageRepository
import com.github.lemaki.notificoin.domain.ad.Ad
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
        private const val ID_ATTRIBUTE = "list_id"
        private const val PUBLICATION_ATTRIBUTE = "index_date"
    }

    fun updateAdsFromWebPage(url: String): List<Ad> {
        val adList = getSortedRemoteAds(url)
        adDataSource.putAll(adList, url)
        return adList
    }

    fun putAll(adList: List<Ad>, url: String) {
        adDataSource.putAll(adList, url)
    }

    fun getAds() = adDataSource.getAll()

    fun getSortedRemoteAds(url: String): List<Ad> {
        val document = webPageRepository.getWebPage(url)
        return documentToAdJsonArrayTransformer.transform(document)?.map { jsonElement ->
            Ad(
                jsonElement.asJsonObject[ID_ATTRIBUTE].asInt,
                jsonElement.asJsonObject[SUBJECT_ATTRIBUTE].asString,
                DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(
                    jsonElement.asJsonObject[PUBLICATION_ATTRIBUTE].asString
                )
            )
        }?.sortedWith(adComparator) ?: throw ParseException("Unable to parse $url", 0)
    }

    fun deleteAll() = adDataSource.deleteAll()
}