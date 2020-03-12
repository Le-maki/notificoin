package com.github.lemaki.notificoin.injection

import com.github.lemaki.core.repository.search.SearchRepository
import com.github.lemaki.repository.NotifiCoinDataBase
import com.github.lemaki.repository.search.SearchDataSource
import com.github.lemaki.repository.search.SearchRepositoryImpl
import org.koin.dsl.module

val searchModule = module {
    single { get<NotifiCoinDataBase>().searchDao() }
    single { SearchDataSource(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get()) }
}