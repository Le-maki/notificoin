package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.repository.searchWithAds.SearchWithAdsRepository
import com.github.corentinc.repository.NotifiCoinDataBase
import com.github.corentinc.repository.searchWithAds.SearchWithAdsDataSource
import com.github.corentinc.repository.searchWithAds.SearchWithAdsRepositoryImpl
import org.koin.dsl.module

val searchWithAdsModule = module {
    single<SearchWithAdsRepository> { SearchWithAdsRepositoryImpl(get(), get(), get(), get()) }
    single { SearchWithAdsDataSource(get()) }
    single { get<NotifiCoinDataBase>().searchWithAdsDao() }
}