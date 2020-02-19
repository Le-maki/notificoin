package com.github.lemaki.notificoin.data.webpage

import com.github.lemaki.notificoin.logger.NotifiCoinLogger
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class WebPageDataSource {
	companion object {
		private val HEADERS = mapOf(
			"Host" to "www.leboncoin.fr",
			"Connection" to "keep-alive",
			"Cache-Control" to "max-age=0",
			"Upgrade-Insecure-Requests" to "1",
			"User-Agent" to " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36",
			"Accept" to " text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
			"Accept-Encoding" to " gzip, deflate, br",
			"Accept-Language" to " fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7"
		)
        private const val TIMEOUT = 10 * 1000
	}

	fun getWebPage(url: String): Document {
		NotifiCoinLogger.i("Trying to GET $url")
        return Jsoup.connect(url).headers(HEADERS).timeout(TIMEOUT).get()
	}
}