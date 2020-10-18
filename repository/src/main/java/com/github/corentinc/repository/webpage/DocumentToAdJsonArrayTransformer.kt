package com.github.corentinc.repository.webpage

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import org.jsoup.nodes.Document
import java.text.ParseException
import java.util.regex.Pattern

class DocumentToAdJsonArrayTransformer {
    companion object {
        private const val DATA_REGEX = "REDIAL_PROPS__ = (\\[.*)"
        private const val AD_LIST_REGEX = "(\\{\"list_id(.*?))((,\"ads_alu)|(,\"ads_shippable)|$)"
        private const val AD_REGEX =
            "(\\{\"list_id(.*?))(?=(,\\{\"list_id)|(,\"ads_shippable)|(,\"ads_alu))"
        private const val TAG_NAME = "script"
        private const val NODE_SELECTOR = "list_id"
    }

    fun transform(document: Document): JsonArray? {
        val dataNode = document.getElementsByTag(TAG_NAME)
            ?.find {
                it.childNodeSize() > 0 && it.childNode(0).toString().contains(NODE_SELECTOR)
            }?.childNode(0)
        val adJsonArray = JsonArray()
        val dataMatcher = Pattern.compile(DATA_REGEX).matcher(dataNode.toString())
        dataMatcher.find()
        dataMatcher.group(1)?.let { data ->
            val adListMatcher = Pattern.compile(AD_LIST_REGEX).matcher(data)
            adListMatcher.find()
            val adList = adListMatcher.group(1)
            adList?.let {
                val adMatcher = Pattern.compile(AD_REGEX).matcher(it)
                while (adMatcher.find()) {
                    try {
                        adJsonArray.add(
                            Gson().fromJson(
                                adMatcher.group(1),
                                JsonElement::class.java
                            )
                        )
                    } catch (exception: Exception) {
                        println()
                    }

                }
            } ?: throw ParseException("Unable to parse the webpage", 0)
        } ?: throw ParseException("Unable to parse the webpage", 0)
        return adJsonArray
    }
}