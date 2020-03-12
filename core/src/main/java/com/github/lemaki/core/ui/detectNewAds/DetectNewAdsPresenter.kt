package com.github.lemaki.core.ui.detectNewAds

interface DetectNewAdsPresenter {
    fun presentBigtextNotification(title: String, text: String, bigText: String)
    fun stopSelf()
    fun presentNewAdNotifications(size: Int, titlesString: String, title: String, url: String)
}