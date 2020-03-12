package com.github.lemaki.notificoin.injection

import com.github.lemaki.core.repository.searchWithAds.SearchWithAdsRepository
import com.github.lemaki.repository.NotifiCoinDataBase
import com.github.lemaki.repository.searchWithAds.SearchWithAdsDataSource
import com.github.lemaki.repository.searchWithAds.SearchWithAdsRepositoryImpl
import org.koin.dsl.module

val searchWithAdsModule = module {
    single<SearchWithAdsRepository> { SearchWithAdsRepositoryImpl(get(), get(), get(), get()) }
    single { SearchWithAdsDataSource(get()) }
    single { get<NotifiCoinDataBase>().searchWithAdsDao() }
}