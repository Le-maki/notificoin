package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.repository.searchWithAds.SearchAdsRepository
import com.github.corentinc.repository.NotifiCoinDataBase
import com.github.corentinc.repository.searchWithAds.SearchAdsPositionDataSource
import com.github.corentinc.repository.searchWithAds.SearchAdsPositionRepositoryImpl
import org.koin.dsl.module

val searchWithAdsModule = module {
    single<SearchAdsRepository> {
        SearchAdsPositionRepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    single { SearchAdsPositionDataSource(get()) }
    single { get<NotifiCoinDataBase>().searchWithAdsDao() }
}