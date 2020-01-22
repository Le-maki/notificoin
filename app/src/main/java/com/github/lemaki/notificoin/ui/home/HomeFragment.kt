package com.github.lemaki.notificoin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.logger.NotifiCoinLogger
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.regex.Pattern

class HomeFragment : Fragment() {

	private lateinit var homeViewModel: HomeViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
		val root = inflater.inflate(R.layout.fragment_home, container, false)
		homeViewModel.text.observe(this, Observer {
			text_home.text = it
		})
		return root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				val ads = convertAdsFromStringToJson(
					extractAdsString(
						getWebPageAsDocument()
					)
				)
				val titles = ads.asJsonArray.map {
					it.asJsonObject["subject"]
				}.toString()
				NotifiCoinLogger.i(titles)
				withContext(Dispatchers.Main) {
					homeViewModel.text.value = titles
				}
			} catch (error: Exception) {
				NotifiCoinLogger.e("ERRO@R FETCHING ", error)
				withContext(Dispatchers.Main) {
					homeViewModel.text.value = "ERROR FETCHING : $error"
				}
			}
		}
	}

	private fun convertAdsFromStringToJson(ads: String?): JsonElement {
		return Gson().fromJson(ads, JsonElement::class.java)
	}

	private fun getWebPageAsDocument(): Document {
		return Jsoup.connect("https://www.leboncoin.fr/recherche/?category=2&text=voiture&locations=Nantes")
				.headers(
					mapOf(
						"Host" to "www.leboncoin.fr",
						"Connection" to "keep-alive",
						"Cache-Control" to "max-age=0",
						"Upgrade-Insecure-Requests" to "1",
						"User-Agent" to " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36",
						"Accept" to " text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
						"Accept-Encoding" to " gzip, deflate, br",
						"Accept-Language" to " fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7"
					)
				)
				.get()

	}

	private fun extractAdsString(document: Document): String? {
		val adsNode = document.getElementsByTag("script")
				?.find { it.childNodeSize() > 0 && it.childNode(0).toString().contains("list_id") }
				?.childNode(0)
		val matcher = Pattern.compile("\"ads\":(.*?),\"ads_alu\"(.*?)")
				.matcher(adsNode.toString())
		matcher.find()
		return matcher.group(1)
	}
}