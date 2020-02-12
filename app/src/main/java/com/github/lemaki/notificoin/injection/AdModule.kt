package com.github.lemaki.notificoin.injection

import com.github.lemaki.notificoin.data.NotifiCoinDataBase
import com.github.lemaki.notificoin.data.ad.AdDataSource
import com.github.lemaki.notificoin.data.ad.AdRepository
import com.github.lemaki.notificoin.domain.ad.Ad
import com.github.lemaki.notificoin.domain.ad.AdDefaultSorter
import com.github.lemaki.notificoin.ui.ad.AdListToAdsListViewModelTransformer
import org.koin.dsl.module

val adModule = module {
    single { get<NotifiCoinDataBase>().adDao() }
    single { AdDataSource(get()) }
    single { AdRepository(get(), get(), get(), get()) }
    single { AdListToAdsListViewModelTransformer() }
    single { AdDefaultSorter() as Comparator<Ad> }
}