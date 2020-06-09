package com.github.corentinc.logger.analytics

import com.github.corentinc.logger.analytics.NotifiCoinEvenScreen.*
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.Screen

enum class NotificoinEvent(val parameters: List<NotifiCoinEventParameter>) {
    HOME_START(listOf(Screen(HOME))),
    LIST_OF_ADS_START(listOf(Screen(LIST_OF_ADS))),
    EDIT_SEARCH_START(listOf(Screen(EDIT_SEARCH))),
    BATTERY_WARNING_START(listOf(Screen(BATTERY_WARNING))),
    SETTINGS_START(listOf(Screen(BATTERY_WARNING)))
}

sealed class NotifiCoinEventParameter(val name: String, open val value: String) {
    data class Screen(val enumvalue: NotifiCoinEvenScreen):
        NotifiCoinEventParameter("SCREEN", enumvalue.name)
}

enum class NotifiCoinEvenScreen {
    HOME, LIST_OF_ADS, EDIT_SEARCH, BATTERY_WARNING
}