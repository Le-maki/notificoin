package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.SearchAdsPostionDefaultSorter
import com.github.corentinc.core.repository.searchWithAds.SearchAdsPositionRepository
import com.github.corentinc.repository.NotifiCoinDataBase
import com.github.corentinc.repository.searchWithAds.SearchAdsPositionDataSource
import com.github.corentinc.repository.searchWithAds.SearchAdsPositionPositionRepositoryImpl
import org.koin.dsl.module

val searchAdsPositionModule = module {
    single<SearchAdsPositionRepository> {
        SearchAdsPositionPositionRepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    single { SearchAdsPositionDataSource(get()) }
    single { get<NotifiCoinDataBase>().searchWithAdsDao() }
    single { SearchAdsPostionDefaultSorter(get()) }
}