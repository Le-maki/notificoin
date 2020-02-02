package com.github.lemaki.notificoin.injection

import com.github.lemaki.notificoin.data.NotifiCoinDataBase
import com.github.lemaki.notificoin.data.searchWithAds.SearchWithAdsDataSource
import com.github.lemaki.notificoin.data.searchWithAds.SearchWithAdsRepository
import org.koin.dsl.module

val searchWithAdsModule = module {
	single { SearchWithAdsRepository(get(), get(), get()) }
	single { SearchWithAdsDataSource(get()) }
	single { get<NotifiCoinDataBase>().searchWithAdsDao() }
}