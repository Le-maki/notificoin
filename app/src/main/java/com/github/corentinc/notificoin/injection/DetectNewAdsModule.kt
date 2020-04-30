package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.DetectNewAdsInteractor
import com.github.corentinc.core.ui.detectNewAds.DetectNewAdsPresenter
import org.koin.dsl.module

val detectNewAdsModule = module {
    single { (detectNewAdsPresenter: DetectNewAdsPresenter) ->
        DetectNewAdsInteractor(
            searchAdsPositionRepository = get(),
            detectNewAdsPresenter = detectNewAdsPresenter
        )
    }
}