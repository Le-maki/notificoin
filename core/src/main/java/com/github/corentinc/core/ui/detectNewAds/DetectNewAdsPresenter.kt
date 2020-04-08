package com.github.corentinc.core.ui.detectNewAds

import com.github.corentinc.core.adList.AdListErrorType

interface DetectNewAdsPresenter {
    fun presentBigtextNotification(title: String, text: String, bigText: String)
    fun stopSelf()
    fun presentNewAdNotifications(size: Int, titlesString: String, title: String, url: String)
    fun presentErrorNotification(errorType: AdListErrorType, exception: Exception)
}