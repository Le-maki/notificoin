package com.github.corentinc.core.search

class SearchPositionDefaultSorter: Comparator<SearchPosition> {
    override fun compare(o1: SearchPosition?, o2: SearchPosition?): Int {
        return compareBy<SearchPosition> { it.position }.compare(o1, o2)
    }
}