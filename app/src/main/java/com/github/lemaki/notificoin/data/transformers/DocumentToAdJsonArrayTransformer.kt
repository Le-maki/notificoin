package com.github.lemaki.notificoin.data.transformers

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import org.jsoup.nodes.Document
import java.util.regex.Pattern

class DocumentToAdJsonArrayTransformer {
	companion object {
		private const val AD_EXTRACTOR_REGEX = "\"ads\":(.*?),\"ads_alu\"(.*?)"
		private const val TAG_NAME = "script"
		private const val NODE_SELECTOR = "list_id"
	}

	fun transform(document: Document): JsonArray? {
		val adsNode = document.getElementsByTag(TAG_NAME)
				?.find {
					it.childNodeSize() > 0 && it.childNode(0).toString().contains(NODE_SELECTOR)
				}?.childNode(0)
		val matcher = Pattern.compile(AD_EXTRACTOR_REGEX).matcher(adsNode.toString())
		matcher.find()
		return Gson().fromJson(matcher.group(1), JsonElement::class.java).asJsonArray
	}
}