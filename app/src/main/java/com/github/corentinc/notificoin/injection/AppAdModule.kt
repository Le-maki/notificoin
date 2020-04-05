package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.ad.AdDefaultSorter
import com.github.corentinc.notificoin.ui.ad.AdListToAdViewModelListTransformer
import com.github.corentinc.repository.NotifiCoinDataBase
import com.github.corentinc.repository.ad.AdDataSource
import com.github.corentinc.repository.ad.AdRepository
import org.koin.dsl.module

val adModule = module {
    single { get<NotifiCoinDataBase>().adDao() }
    single { AdDataSource(get()) }
    single { AdRepository(get(), get(), get(), get()) }
    single { AdListToAdViewModelListTransformer(get()) }
    single<Comparator<Ad>> { AdDefaultSorter() }
}