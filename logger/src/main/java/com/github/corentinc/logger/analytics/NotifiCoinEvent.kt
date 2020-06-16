package com.github.corentinc.logger.analytics

import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.*

open class NotifiCoinEvent(val key: String, val parameters: List<NotifiCoinEventParameter?>) {
    class ScreenStarted(screen: Screen): NotifiCoinEvent("SCREEN_START", listOf(screen))
    class ExceptionThrown(
        exception: EventException,
        screen: Screen? = null,
        popUp: PopUp? = null
    ): NotifiCoinEvent("EXCEPTION_THROWN", listOf(screen, exception, popUp))

    class PopUpShown(popUp: PopUp, screen: Screen):
        NotifiCoinEvent("POP_UP_SHOWN", listOf(screen, popUp))

    class SearchChanged(searchStatus: SearchStatus):
        NotifiCoinEvent("SEARCH_CHANGED", listOf(searchStatus))

    class ButtonClicked(
        buttonName: ButtonName,
        screen: Screen? = null,
        popUp: PopUp? = null
    ): NotifiCoinEvent("BUTTON_CLICKED", listOf(screen, buttonName, popUp))
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