package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.SearchAdsPostionDefaultSorter
import com.github.corentinc.core.repository.searchAdsPosition.SearchAdsPositionRepository
import com.github.corentinc.repository.NotifiCoinDataBase
import com.github.corentinc.repository.searchWithAds.SearchAdsPositionDataSource
import com.github.corentinc.repository.searchWithAds.SearchAdsPositionPositionRepositoryImpl
import org.koin.dsl.module

val searchAdsPositionModule = module {
    single<SearchAdsPositionRepository> {
        SearchAdsPositionPositionRepositoryImpl(
            searchAdsPositionDataSource = get(),
            searchRepository = get(),
            adRepository = get(),
            searchPositionRepository = get(),
            adComparator = get()
        )
    }
    single { SearchAdsPositionDataSource(searchAdsPositionDao = get()) }
    single { get<NotifiCoinDataBase>().searchWithAdsDao() }
    single { SearchAdsPostionDefaultSorter(searchPositionDefaultSorter = get()) }
}