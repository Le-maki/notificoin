package com.github.lemaki.notificoin.injection

import com.github.lemaki.core.DetectNewAdsInteractor
import com.github.lemaki.core.ui.detectNewAds.DetectNewAdsPresenter
import org.koin.dsl.module

val detectNewAdsModule = module {
    single { (detectNewAdsPresenter: DetectNewAdsPresenter) ->
        DetectNewAdsInteractor(
            get(),
            detectNewAdsPresenter
        )
    }
}