package com.github.lemaki.notificoin.injection

import com.github.lemaki.core.ad.Ad
import com.github.lemaki.core.ad.AdDefaultSorter
import com.github.lemaki.notificoin.ui.ad.AdListToAdsListViewModelTransformer
import com.github.lemaki.repository.NotifiCoinDataBase
import com.github.lemaki.repository.ad.AdDataSource
import com.github.lemaki.repository.ad.AdRepository
import org.koin.dsl.module

val adModule = module {
    single { get<NotifiCoinDataBase>().adDao() }
    single { AdDataSource(get()) }
    single { AdRepository(get(), get(), get(), get()) }
    single { AdListToAdsListViewModelTransformer() }
    single<Comparator<Ad>> { AdDefaultSorter() }
}