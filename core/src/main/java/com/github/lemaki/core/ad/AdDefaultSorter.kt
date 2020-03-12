package com.github.lemaki.core.ad

class AdDefaultSorter: Comparator<Ad> {
    override fun compare(o1: Ad?, o2: Ad?): Int {
        return compareByDescending<Ad> { it.publicationDate }.thenBy { it.title }.compare(o1, o2)
    }
}