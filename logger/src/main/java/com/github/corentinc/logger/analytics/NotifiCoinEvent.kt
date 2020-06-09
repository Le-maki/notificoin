package com.github.corentinc.logger.analytics

import com.github.corentinc.logger.analytics.NotifiCoinEventException.*
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.*
import com.github.corentinc.logger.analytics.NotifiCoinEventPopUp.DEFAULT
import com.github.corentinc.logger.analytics.NotifiCoinEventPopUp.SPECIAL_CONSTRUCTOR
import com.github.corentinc.logger.analytics.NotifiCoinEventScreen.*

enum class NotifiCoinEvent(val parameters: List<NotifiCoinEventParameter>) {
    HOME_START(listOf(Screen(HOME))),
    LIST_OF_ADS_START(listOf(Screen(LIST_OF_ADS))),
    EDIT_SEARCH_START(listOf(Screen(EDIT_SEARCH))),
    BATTERY_WARNING_START(listOf(Screen(BATTERY_WARNING))),
    SETTINGS_START(listOf(Screen(BATTERY_WARNING))),
    LIST_OF_ADS_CONNECTION_ERROR(
        listOf(
            EventException(CONNECTION),
            Screen(LIST_OF_ADS)
        )
    ),
    LIST_OF_ADS_PARSING_ERROR(
        listOf(
            EventException(PARSING),
            Screen(LIST_OF_ADS)
        )
    ),
    LIST_OF_ADS_FORBIDDEN_ERROR(
        listOf(
            EventException(FORBIDDEN),
            Screen(LIST_OF_ADS)
        )
    ),
    LIST_OF_ADS_UNKNOWN_ERROR(
        listOf(
            EventException(UNKNOWN),
            Screen(LIST_OF_ADS)
        )
    ),
    DETECT_NEW_ADS_UNKNOWN_ERROR(
        listOf(
            EventException(UNKNOWN)
        )
    ),
    DETECT_NEW_ADS_FORBIDDEN_ERROR(
        listOf(
            EventException(FORBIDDEN)
        )
    ),
    BATTERY_WARNING_DEFAULT_POPUP(
        listOf(
            PopUp(DEFAULT),
            Screen(BATTERY_WARNING)
        )
    ),
    BATTERY_WARNING_SPECIAL_POPUP(
        listOf(
            PopUp(SPECIAL_CONSTRUCTOR),
            Screen(BATTERY_WARNING)
        )
    ),
    BATTERY_WARNING_NO_APP_FOR_INTENT(
        listOf(
            PopUp(SPECIAL_CONSTRUCTOR),
            EventException(NO_APP_FOR_INTENT),
            Screen(BATTERY_WARNING)
        )
    ),
    EDIT_SEARCH_SAVED(
        listOf(
            Screen(EDIT_SEARCH),
            SearchStatus(NotifiCoinEventSearchStatus.SEARCH_SAVED)
        )
    ),
    EDIT_SEARCH_DELETED(
        listOf(
            Screen(EDIT_SEARCH),
            SearchStatus(NotifiCoinEventSearchStatus.SEARCH_DELETED)
        )
    )
}

sealed class NotifiCoinEventParameter(val name: String, open val value: String) {
    data class Screen(val enumvalue: NotifiCoinEventScreen):
        NotifiCoinEventParameter("SCREEN", enumvalue.name)

    data class EventException(val enumvalue: NotifiCoinEventException):
        NotifiCoinEventParameter("EXCEPTION", enumvalue.name)

    data class PopUp(val enumvalue: NotifiCoinEventPopUp):
        NotifiCoinEventParameter("POPUP", enumvalue.name)

    data class SearchStatus(val enumvalue: NotifiCoinEventSearchStatus):
        NotifiCoinEventParameter("POPUP", enumvalue.name)
}

enum class NotifiCoinEventScreen {
    HOME, LIST_OF_ADS, EDIT_SEARCH, BATTERY_WARNING
}

enum class NotifiCoinEventException {
    CONNECTION, PARSING, FORBIDDEN, UNKNOWN, NO_APP_FOR_INTENT
}

enum class NotifiCoinEventPopUp {
    DEFAULT, SPECIAL_CONSTRUCTOR
}

enum class NotifiCoinEventSearchStatus {
    SEARCH_SAVED, SEARCH_DELETED
}