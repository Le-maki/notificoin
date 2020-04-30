package  com.github.corentinc.notificoin.ui.adList

import com.github.corentinc.core.SearchAdsPosition
import com.github.corentinc.core.ad.Ad

class AdListToAdViewModelListTransformer(
    private val adComparator: Comparator<Ad>
) {
    fun transform(searchAdsPositionList: List<SearchAdsPosition>): MutableList<AdViewModel> {
        val adSearchTitlePairList = searchAdsPositionList.fold(
            mutableListOf<Pair<Ad, String>>(),
            { acc, searchAdsPosition ->
                acc.addAll(
                    searchAdsPosition.ads.map {
                        Pair(it, searchAdsPosition.search.title)
                    }
                )
                acc
        })
        adSearchTitlePairList.sortWith(Comparator { o1, o2 ->
            adComparator.compare(
                o1?.first,
                o2?.first
            )
        })
        return adSearchTitlePairList.map {
            AdViewModel(
                it.first.title,
                it.second,
                it.first.publicationDate.toString("HH:mm"),
                it.first.publicationDate.toString("dd/MM"),
                it.first.price?.toString(),
                it.first.url
            )
        }.toMutableList()
    }
}