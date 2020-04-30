package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.ad.Ad
import com.github.corentinc.core.ad.AdDefaultSorter
import com.github.corentinc.notificoin.ui.adList.AdListToAdViewModelListTransformer
import com.github.corentinc.repository.NotifiCoinDataBase
import com.github.corentinc.repository.ad.AdDataSource
import com.github.corentinc.repository.ad.AdRepository
import org.koin.dsl.module

val adModule = module {
    single { get<NotifiCoinDataBase>().adDao() }
    single { AdDataSource(adDao = get()) }
    single {
        AdRepository(
            webPageRepository = get(),
            documentToAdJsonArrayTransformer = get(),
            adDataSource = get(),
            adComparator = get()
        )
    }
    single {
        AdListToAdViewModelListTransformer(
            adComparator = get()
        )
    }
    single<Comparator<Ad>> { AdDefaultSorter() }
}