package com.github.lemaki.repository.webpage

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import org.jsoup.nodes.Document
import java.text.ParseException
import java.util.regex.Pattern

class DocumentToAdJsonArrayTransformer {
    companion object {
        private const val DATA_REGEX = "REDIAL_PROPS__ = (\\[.*])"
        private const val TAG_NAME = "script"
        private const val NODE_SELECTOR = "list_id"
        private const val DATA_SELECTOR = "data"
        private const val ADS_SELECTOR = "ads"
    }

    fun transform(document: Document): JsonArray? {
        val dataNode = document.getElementsByTag(TAG_NAME)
            ?.find {
                it.childNodeSize() > 0 && it.childNode(0).toString().contains(NODE_SELECTOR)
            }?.childNode(0)
        val dataMatcher = Pattern.compile(DATA_REGEX).matcher(dataNode.toString())
        dataMatcher.find()
        val jsonArray = Gson().fromJson(dataMatcher.group(1), JsonElement::class.java).asJsonArray
        if (jsonArray.size() >= 6) {
            return jsonArray[5].asJsonObject.get(DATA_SELECTOR).asJsonObject.get(
                ADS_SELECTOR
            ).asJsonArray
        } else {
            throw ParseException("Unable to parse the webpage", 0)
        }
    }
}