package com.github.corentinc.logger.analytics

import com.github.corentinc.logger.analytics.NotifiCoinEventButtonName.*
import com.github.corentinc.logger.analytics.NotifiCoinEventException.*
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.*
import com.github.corentinc.logger.analytics.NotifiCoinEventPopUp.DEFAULT
import com.github.corentinc.logger.analytics.NotifiCoinEventPopUp.SPECIAL_CONSTRUCTOR
import com.github.corentinc.logger.analytics.NotifiCoinEventScreen.*
import com.github.corentinc.logger.analytics.NotifiCoinEventSearchStatus.SEARCH_DELETED
import com.github.corentinc.logger.analytics.NotifiCoinEventSearchStatus.SEARCH_SAVED

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
    BATTERY_WARNING_DEFAULT_POPUP_SHOW(
        listOf(
            PopUp(DEFAULT),
            Screen(BATTERY_WARNING)
        )
    ),
    BATTERY_WARNING_SPECIAL_POPUP_SHOW(
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
            SearchStatus(SEARCH_SAVED)
        )
    ),
    EDIT_SEARCH_DELETED(
        listOf(
            Screen(EDIT_SEARCH),
            SearchStatus(SEARCH_DELETED)
        )
    ),
    ADD_SEARCH_CLICKED(
        listOf(
            Screen(HOME),
            ButtonName(ADD_SEARCH)
        )
    ),
    DEFAULT_OK_CLICKED(
        listOf(
            Screen(BATTERY_WARNING),
            PopUp(DEFAULT),
            ButtonName(OK)
        )
    ),
    DEFAULT_MAYBE_LATER_CLICKED(
        listOf(
            Screen(BATTERY_WARNING),
            ButtonName(MAYBE_LATER),
            PopUp(DEFAULT)
        )
    ),
    DEFAULT_STOP_ASKING_CLICKED(
        listOf(
            Screen(BATTERY_WARNING),
            ButtonName(STOP_ASKING),
            PopUp(DEFAULT)
        )
    ),
    SPECIAL_OK_CLICKED(
        listOf(
            Screen(BATTERY_WARNING),
            ButtonName(OK),
            PopUp(SPECIAL_CONSTRUCTOR)
        )
    ),
    SPECIAL_MAYBE_LATER_CLICKED(
        listOf(
            Screen(BATTERY_WARNING),
            ButtonName(MAYBE_LATER),
            PopUp(SPECIAL_CONSTRUCTOR)
        )
    ),
    SPECIAL_STOP_ASKING_CLICKED(
        listOf(
            Screen(BATTERY_WARNING),
            ButtonName(STOP_ASKING),
            PopUp(SPECIAL_CONSTRUCTOR)
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
        NotifiCoinEventParameter("SEARCH_STATUS", enumvalue.name)

    data class ButtonName(val enumvalue: NotifiCoinEventButtonName):
        NotifiCoinEventParameter("BUTTON_NAME", enumvalue.name)
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

enum class NotifiCoinEventButtonName {
    ADD_SEARCH, OK, MAYBE_LATER, STOP_ASKING
}