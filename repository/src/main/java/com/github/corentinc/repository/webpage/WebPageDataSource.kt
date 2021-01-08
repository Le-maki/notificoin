package com.github.corentinc.repository.webpage

import com.github.corentinc.logger.NotifiCoinLogger
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class WebPageDataSource {
    companion object {
        private val HEADERS = mapOf(
            "Host" to "www.leboncoin.fr",
            "Connection" to "keep-alive",
            "Cache-Control" to "max-age=0",
            "Upgrade-Insecure-Requests" to "1",
            "User-Agent" to " Mozilla/ ${(3..5).random()}.0 (Windows NT 10.0; Win64; x64) AppleWebKit/536.36 (KHTML, like Gecko) Chrome/${(68..87).random()}.0.${(0..100).random()}.106 Safari/537.36",
            "Accept" to " text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.${(7..9).random()}",
            "Sec-Fetch-Site" to "none",
            "Sec-Fetch-Mode" to "navigate",
            "Sec-Fetch-User" to "?1",
            "Sec-Fetch-Dest" to "document",
            "sec-ch-ua" to "\"Google Chrome\";v=\"87\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"87\"",
            "sec-ch-ua-mobile" to "?0",
            "Accept-Encoding" to " gzip, deflate, br",
            "Accept-Language" to " fr-FR,fr;q=0.${(7..9).random()},en-US;q=0.${(7..9).random()},en;q=0.${(7..9).random()}",
            "Cookie" to "didomi_token=eyJ1c2VyX2lkIjoiMTc2ZDMwMjMtZDczNC02YmY4LWJlZGItMDhhMTAxYTdhYTgxIiwiY3JlYXRlZCI6IjIwMjEtMDEtMDVUMTQ6NDU6MDguMTM2WiIsInVwZGF0ZWQiOiIyMDIxLTAxLTA1VDE0OjQ1OjA4LjEzNloiLCJ2ZXJzaW9uIjpudWxsfQ==; datadome=5~yJ5~YIPSEloFK1INxky1hSWq-XPXdgOa9TXZrI5eWdFB.Rt79KXKE_WhTvaW9WTkOC38jfqn.NJ4j_LYyGG2DFrOgb_qZ57TxwHe53rm"
        )
        private const val TIMEOUT = 10 * 1000
    }

    fun getWebPage(url: String): Document {
        NotifiCoinLogger.i("Trying to GET $url")
        return Jsoup.connect(url).headers(HEADERS).timeout(TIMEOUT).get()
    }
}