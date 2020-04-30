package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.repository.search.SearchRepository
import com.github.corentinc.repository.NotifiCoinDataBase
import com.github.corentinc.repository.search.SearchDataSource
import com.github.corentinc.repository.search.SearchRepositoryImpl
import org.koin.dsl.module

val searchModule = module {
    single { get<NotifiCoinDataBase>().searchDao() }
    single { SearchDataSource(searchDao = get()) }
    single<SearchRepository> {
        SearchRepositoryImpl(
            searchDataSource = get(),
            searchPositionRepository = get()
        )
    }
}