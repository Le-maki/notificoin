package com.github.lemaki.notificoin.injection

import com.github.lemaki.notificoin.data.NotifiCoinDataBase
import com.github.lemaki.notificoin.data.search.SearchDataSource
import com.github.lemaki.notificoin.data.search.SearchRepository
import org.koin.dsl.module

val searchModule = module {
	single { get<NotifiCoinDataBase>().searchDao() }
	single { SearchDataSource(get()) }
	single { SearchRepository(get()) }
}