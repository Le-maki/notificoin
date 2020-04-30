package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.repository.search.SearchPositionRepository
import com.github.corentinc.core.search.SearchPositionDefaultSorter
import com.github.corentinc.repository.NotifiCoinDataBase
import com.github.corentinc.repository.searchPosition.SearchPositionDataSource
import com.github.corentinc.repository.searchPosition.SearchPositionRepositoryImpl
import org.koin.dsl.module

val searchPositionModule = module {
    single { get<NotifiCoinDataBase>().searchPositionDao() }
    single { SearchPositionDataSource(searchPositionDao = get()) }
    single<SearchPositionRepository> { SearchPositionRepositoryImpl(searchPositionDataSource = get()) }
    single { SearchPositionDefaultSorter() }
}