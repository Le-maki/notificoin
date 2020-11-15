package com.github.corentinc.repository.webpage

import com.github.corentinc.core.adList.WebPageParsingException
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import org.jsoup.nodes.Document
import org.jsoup.nodes.Node
import java.util.regex.Pattern

class DocumentToAdJsonArrayTransformer {
    companion object {
        private const val AD_LIST_REGEX = "(\\{\"list_id(.*?))((,\"ads_alu)|(,\"ads_shippable)|$)"
        private const val AD_REGEX =
            "(\\{\"list_id(.*?))(?=(,\\{\"list_id)|(,\"ads_shippable)|(,\"ads_alu))"
        private const val TAG_NAME = "script"
        private const val NODE_SELECTOR = "list_id"
    }

    fun transform(document: Document): JsonArray? {
        getDataNode(document)?.let { data ->
            getAdListString(data.toString())?.let { adListString ->
                return transformAdListStringToJsonArray(adListString)
            } ?: throw WebPageParsingException()
        } ?: throw WebPageParsingException()
    }

    private fun getDataNode(document: Document): Node? {
        return document.getElementsByTag(TAG_NAME)
            ?.find {
                it.childNodeSize() > 0 && it.childNode(0).toString().contains(NODE_SELECTOR)
            }?.childNode(0)
    }

    private fun getAdListString(data: String): String? {
        val adListMatcher = Pattern.compile(AD_LIST_REGEX).matcher(data)
        adListMatcher.find()
        return adListMatcher.group(1)
    }

    private fun transformAdListStringToJsonArray(adListString: String): JsonArray {
        val adJsonArray = JsonArray()
        val adMatcher = Pattern.compile(AD_REGEX).matcher(adListString)
        while (adMatcher.find()) {
            adJsonArray.add(
                Gson().fromJson(
                    adMatcher.group(1),
                    JsonElement::class.java
                )
            )
        }
        return adJsonArray
    }
}