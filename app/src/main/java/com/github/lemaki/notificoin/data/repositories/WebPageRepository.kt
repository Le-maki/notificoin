package com.github.lemaki.notificoin.data.repositories

import com.github.lemaki.notificoin.data.dataSources.WebPageDataSource
import org.jsoup.nodes.Document

class WebPageRepository(private val webPageDataSource: WebPageDataSource) {
	fun getWebPage(url: String): Document {
		return webPageDataSource.getWebPage(url)
	}
}