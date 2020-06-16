package com.github.corentinc.logger.analytics

import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.*

open class NotifiCoinEvent(val key: EventKey, val parameters: List<NotifiCoinEventParameter?>) {
    class ScreenStarted(key: EventKey, screen: Screen): NotifiCoinEvent(key, listOf(screen))
    class ExceptionThrown(
        key: EventKey,
        exception: EventException,
        screen: Screen? = null,
        popUp: PopUp? = null
    ): NotifiCoinEvent(key, listOf(screen, exception, popUp))

    class PopUpShown(key: EventKey, popUp: PopUp, screen: Screen):
        NotifiCoinEvent(key, listOf(screen, popUp))

    class SearchChanged(key: EventKey, searchStatus: SearchStatus):
        NotifiCoinEvent(key, listOf(searchStatus))

    class ButtonClicked(
        key: EventKey,
        buttonName: ButtonName,
        screen: Screen? = null,
        popUp: PopUp? = null
    ): NotifiCoinEvent(key, listOf(screen, buttonName, popUp))
}

enum class EventKey {
    HOME_START,
    LIST_OF_ADS_START,
    EDIT_SEARCH_START,
    BATTERY_WARNING_START,
    SETTINGS_START,
    LIST_OF_ADS_CONNECTION_ERROR,
    LIST_OF_ADS_PARSING_ERROR,
    LIST_OF_ADS_FORBIDDEN_ERROR,
    LIST_OF_ADS_UNKNOWN_ERROR,
    DETECT_NEW_ADS_UNKNOWN_ERROR,
    DETECT_NEW_ADS_FORBIDDEN_ERROR,
    BATTERY_WARNING_DEFAULT_POPUP_SHOW,
    BATTERY_WARNING_SPECIAL_POPUP_SHOW,
    BATTERY_WARNING_NO_APP_FOR_INTENT,
    EDIT_SEARCH_SAVED,
    EDIT_SEARCH_DELETED,
    ADD_SEARCH_CLICKED,
    DEFAULT_OK_CLICKED,
    DEFAULT_MAYBE_LATER_CLICKED,
    DEFAULT_STOP_ASKING_CLICKED,
    SPECIAL_OK_CLICKED,
    SPECIAL_MAYBE_LATER_CLICKED,
    SPECIAL_STOP_ASKING_CLICKED
}

open class NotifiCoinEventParameter(val name: String, open val value: String) {
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
    HOME, LIST_OF_ADS, EDIT_SEARCH, BATTERY_WARNING, SETTINGS
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