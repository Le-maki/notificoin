package com.github.corentinc.core

import com.github.corentinc.core.search.SearchPositionDefaultSorter

class SearchAdsPostionDefaultSorter(
    private val searchPositionDefaultSorter: SearchPositionDefaultSorter
): Comparator<SearchAdsPosition> {
    override fun compare(o1: SearchAdsPosition?, o2: SearchAdsPosition?): Int {
        o1?.let {
            o2?.let {
                return searchPositionDefaultSorter.compare(o1.searchPosition, o2.searchPosition)
            }
        }
        return 0
    }
}