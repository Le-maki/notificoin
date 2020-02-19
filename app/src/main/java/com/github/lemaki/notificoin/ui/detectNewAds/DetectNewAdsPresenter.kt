package com.github.lemaki.notificoin.ui.detectNewAds

interface DetectNewAdsPresenter {
    fun presentBigtextNotification(title: String, text: String, bigText: String)
    fun presentNewAdNotifications(size: Int, titlesString: String, title: String)
    fun stopSelf()
}