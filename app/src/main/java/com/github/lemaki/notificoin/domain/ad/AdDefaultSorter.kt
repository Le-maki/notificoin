package com.github.lemaki.notificoin.domain.ad

object AdDefaultSorter {
    fun getInstance(): Comparator<Ad> {
        return compareByDescending<Ad> { it.publicationDate }.thenBy { it.title }
    }
}